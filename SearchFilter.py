from PyQt4.QtGui import *
from PyQt4.QtCore import *


class SearchFilter:
    
    Widget = None
    target = None
    rgx = None
    
    def __init__(self, Widget, target):
        self.Widget = Widget
        self.target = target
        self.rgx = QRegExp(target, Qt.CaseInsensitive, QRegExp.Wildcard)

        
    
    def search_treewidget(self):
        path = self.target.split("/")
        cnt = self.Widget.topLevelItemCount()
        root = []
        for i in range(0, cnt):
            root.append(self.Widget.topLevelItem(i))
        
        for item in path:
  
            flag = 0

            for r in root:
                
                if r.text(0) == item:
                    flag = 1
                    childCount = r.childCount()
                    newroot = []
                    for ch in range(0, childCount):
                        newroot.append(r.child(ch))
                    
                    # expand this item
                    r.setExpanded(1)
                    r.setSelected(1)
                    
                else:
                    r.setHidden(1)
                
            if flag == 0:
                for i in range(0, cnt):
                    self.Widget.topLevelItem(i).setHidden(1)
                break
                
            del root[:]
            root = newroot[:]
        
        


        
    def filter_treewidget(self):
        
        cnt = self.Widget.topLevelItemCount()
     
        root = []
        for i in range(0, cnt):
            root.append(self.Widget.topLevelItem(i))
        
        # set all qtreewidgetitems to be hidden
        it = QTreeWidgetItemIterator(self.Widget)
        while it.value():
            item = it.value()
            item.setHidden(1)
            it = it.__iadd__(1)
        
        
        result = []
        
        for r in root:
            del result[:]
            self.findChild(r, result)
            for i in result:
                # expand this item
                i.setExpanded(1)
                i.setSelected(1)
                
                parent = i
                while parent != None:
                    parent.setHidden(0)
                    parent.setExpanded(1)
                    parent = parent.parent()



    def findChild(self, root, result):
        childCount = root.childCount()
        if childCount == 0:
            print root.text(0)
            if root.text(0).contains(self.rgx):
                result.append(root) 
                return
        else:
            for ch in range(0, childCount):
                self.findChild(root.child(ch), result)
                
    
    def search_listwidget(self):
        total = self.Widget.count()
        for i in range(0, total):
            item = self.Widget.item(i)
            if item.text() == self.target:
                item.setHidden(0)
            else:
                item.setHidden(1)

        
    def filter_listwidget(self):
        total = self.Widget.count()
        for i in range(0, total):
            item = self.Widget.item(i)
            if item.text().contains(self.rgx):
                item.setHidden(0)
            else:
                item.setHidden(1)

        
