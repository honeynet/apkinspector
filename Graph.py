from PyQt4.QtGui import *
from PyQt4.QtCore import *
import sys
from Ui_MainWindow import Ui_mainWindow
from  InputDialog import *
from Ui_InputDialog import Ui_inputDialog_dalvik


class GraphicsView(QGraphicsView):
    scene = None
    view = None
    pageSize = [100, 100]
    root = None
    nodeList = []
    
    def __init__(self):
        super(GraphicsView, self).__init__()

        # set the drag mode
        self.setDragMode(QGraphicsView.ScrollHandDrag)
           
    def wheelEvent(self, event):
        factor = 1.41 ** (event.delta()/240.0)
        self.scale(factor, factor)
    
    def setPageSize(self, pagesize):
        self.pageSize[0] = pagesize[0]
        self.pageSize[1] = pagesize[1]
    

    def initShow(self, tab_cfg,  gridLayout, tabWidget, dalvikEdit):
        self.view = GraphicsView()
        self.scene = QGraphicsScene()
        self.scene.setSceneRect(0, 0, self.pageSize[0], self.pageSize[1])
        self.view.setScene(self.scene)
        gridLayout.addWidget(self.view)
        import Global 
        Global.TABWIDGET = tabWidget
        Global.DALVIKEDIT = dalvikEdit

    
    def show(self, nodeList, linkList):    
        self.scene.clear()
        self.scene.setSceneRect(0, 0, self.pageSize[0]*2, self.pageSize[1]*2)
        del self.nodeList[:]
        import copy
        self.nodeList = copy.copy(nodeList)
        
        #for node in nodeList:
        #    self.scene.addItem(node)
        #for link in linkList:
        #    self.scene.addItem(link)
        #self.root = QGraphicsItemGroup(None, self.scene)
        
        # I just want to crate a root QGraphicsItem object, which is None. So it creates a Link object for convenience.
        # Qt has a bug in translate function. I crate a root item to implement translate function.
        self.root = Link()
        
        for node in nodeList:
            node.setParentItem(self.root)
        for link in linkList:
            link.setParentItem(self.root)
        self.scene.addItem(self.root)
        
        self.root.translate(0, 0)


class Link(QGraphicsLineItem):
    painterPath = None
    color = None
    
    def __init__(self):
        QGraphicsLineItem.__init__(self)        
        self.painterPath = QPainterPath()
        self.myColor = Qt.black
        self.setFlags(QGraphicsLineItem.ItemIsMovable | QGraphicsLineItem.ItemIsSelectable)

    
    def drawLine(self, path):
        for i in range(0, len(path), 2):
            x = path[i]
            y = path[i+1]
            if i == 0:
                self.painterPath.moveTo(x, y)
            else:
                self.painterPath.lineTo(x, y)


    def drawArrow(self, points):
        point1_x = points[0]
        point1_y = points[1]
        point2_x = points[2]
        point2_y = points[3]
        point3_x = points[4]
        point3_y = points[5]
        self.painterPath.moveTo(point1_x, point1_y)
        self.painterPath.lineTo(point2_x, point2_y)
        self.painterPath.lineTo(point3_x, point3_y)
        self.painterPath.lineTo(point1_x, point1_y)


    def color(self):
        return self.myColor

    def setColor(self, color):
        colorDir = {"red":Qt.red, "blue":Qt.blue, "green":Qt.green}
        self.myColor = colorDir[color]

    def paint(self, painter, option, widget = None):
        pen = QPen(self.myColor)  
        painter.setPen(pen)
        painter.drawPath(self.painterPath)
        
    def boundingRect(self):
        rect = QRectF(0, 0, 99999999, 99999999)
        return rect         
        
        
 
        
class Node(QGraphicsItem):
    myText = None
    myTextColor = None
    myBackgroundColor = None
    myOutlineColor = None
    myLinks = []
    left = 0.0
    top = 0.0
    width = 0.0
    height = 0.0
    font = None
    hint = None
    minOffset = 0
    maxOffset = 0
    
    
    def __init__(self, aleft = 0.0, atop = 0.0, awidth = 0.0, aheight = 0.0):
        QGraphicsItem.__init__(self)
        self.myTextColor = QColor(Qt.darkGreen)
        self.myOutlineColor = QColor(Qt.darkGray)
        self.myBackgroundColor = QColor(Qt.white)
#        self.setFlags(QGraphicsItem.ItemIsMovable | QGraphicsItem.ItemIsSelectable)
        self.setFlags(QGraphicsItem.ItemIsSelectable | QGraphicsItem.ItemIsFocusable)
        self.left = aleft
        self.top = atop
        self.width = awidth
        self.height = aheight
        self.font = QFont("Courier", 5, -1, 0)
        self.setAcceptHoverEvents(1)

    
    def setText(self, text):
        self.prepareGeometryChange()
        self.myText = str(text)
        self.update()
        # after getting the text, it can get the min and max offset
        self.getMinMaxOffset()
    
    def getMinMaxOffset(self):
        text =  self.myText.split("\n")
        firstLine = text[0]
        endLine = text[-2]
        self.minOffset = firstLine[:firstLine.index(" ")]
        self.maxOffset = endLine[:endLine.index(" ")]
        # change the string type to hex type
        self.minOffset = int(self.minOffset, 16)
        self.maxOffset = int(self.maxOffset, 16)
    
    def text(self):
        return self.myText
    
    def setTextColor(self, color):
        self.myTextColor = color
        self.update()
    
    def textColor(self):
        return self.myTextColor
    
    def setOutlineColor(self, color):
        self.myOutlineColor = color
        self.update()
        
    def outlineColor(self):
        return self.myOutlineColor
    
    def setBackgroundColor(self, color):
        self.myBackgroundColor = color
        self.update()
    
    def backgroundColor(self):
        return self.myBackgroundColor
        
    def addLink(self, link):
        self.myLinks.append(link)
        
    def removeLink(self, link):
        self.myLinks.remove(link)    
        
    def outlineRect(self):
        rect = QRectF(self.left, self.top, self.width, self.height)
        return rect 
    
    def updateOutlineRect(self):
        Padding = 8
        metrics = QFontMetrics(self.font)
        rect = metrics.boundingRect(self.myText)
        rect.adjust(-Padding, -Padding, +Padding, +Padding)
        rect.translate(-rect.center())
        return rect
        
    
    def boundingRect(self):
        Margin = 1
        return self.outlineRect().adjusted(-Margin, -Margin, +Margin, +Margin)
    
    def paint(self, painter, option, widget = None):
        pen = QPen(self.myOutlineColor)
        if option.state & QStyle.State_Selected:
            pen.setStyle(Qt.DotLine)
            pen.setWidth(2)
            
        painter.setPen(pen)
        painter.setBrush(self.myBackgroundColor)
        rect = self.outlineRect()
        painter.drawRect(rect)
        painter.setPen(self.myTextColor)
        
        painter.setFont(self.font)
        painter.drawText(rect, Qt.AlignLeft | Qt.AlignVCenter, self.myText)
    
    def mouseDoubleClickEvent(self, event):
        
#        inputDialog = QInputDialog()
#        ok = None
#        text = QString
#        (text, ok) = inputDialog.getText(event.widget(), str("Edit Text"), str("Edit new Text:"), QLineEdit.Normal, self.myText)

#        if ok and not text.isEmpty():
#            self.setText(text)

        inputDialog_dalvik = inputDialog(self, self.myText)
        inputDialog_dalvik.exec_()

    def hoverEnterEvent(self, event):
        scene = self.scene()
        pos = event.scenePos()
        self.hint = Hint(pos.x(), pos.y(), self.width, self.height, self.myText)
        scene.addItem(self.hint)
    
    def hoverLeaveEvent(self, event):
        scene = self.scene()
        scene.removeItem(self.hint)
        del self.hint
    
    def keyPressEvent(self, event):
        # If the user press the space key, the view will change to Dalvik from CFG.
        if event.key() == Qt.Key_Space:
            startLineNumber = "0x" + self.myText[:self.myText.index(" ")]
            lineNumber = len(self.myText.split("\n")) - 1
            import Global
            Global.TABWIDGET.setCurrentIndex(1)
            # set the cursor's background color 
            colorFormat = QTextCharFormat()
            colorFormat.setBackground(QColor(Qt.gray).lighter(145))
            document = Global.DALVIKEDIT.document()
            cursor = document.find(startLineNumber + " ")
            for i in range(0, lineNumber - 1):
                cursor.movePosition(QTextCursor.Down, QTextCursor.KeepAnchor)
            cursor.movePosition(QTextCursor.EndOfLine, QTextCursor.KeepAnchor)
            cursor.mergeCharFormat(colorFormat)
            Global.DALVIKEDIT.setTextCursor(cursor)
            Global.DALVIKEDIT.ensureCursorVisible()


class Hint(QGraphicsItem):
    text = None
    left = 0.0
    top = 0.0
    width = 0.0
    height = 0.0
    
    def __init__(self, aleft = 0.0, atop = 0.0, awidth = 0.0, aheight = 0.0, text = None):
        QGraphicsItem.__init__(self)
        self.text = text
        self.left = aleft
        self.top = atop
        self.width = awidth
        self.height = aheight
    
    def outlineRect(self):
        rect = QRectF(self.left, self.top, self.width, self.height)
        return rect 
 
    def boundingRect(self):
        Margin = 1
        return self.outlineRect().adjusted(-Margin, -Margin, +Margin, +Margin)  

    def paint(self, painter, option, widget = None):
        pen = QPen(Qt.white)
        if option.state & QStyle.State_Selected:
            pen.setStyle(Qt.DotLine)
            pen.setWidth(2)
        painter.setPen(pen)
        brushColor = QColor(Qt.gray).lighter(140)
        painter.setBrush(brushColor)
        rect = self.outlineRect()
        painter.drawRect(rect)
        painter.setPen(Qt.blue)
        painter.setFont(QFont("Courier", 5, -1, 0))
        painter.drawText(rect, Qt.AlignLeft | Qt.AlignVCenter, self.text)    
    
    

