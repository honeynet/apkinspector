# -*- coding: utf-8 -*-

"""
Module implementing FindDialog.
"""
from PyQt4.QtGui import *
from PyQt4.QtCore import *
from PyQt4.QtGui import QDialog
from PyQt4.QtCore import pyqtSignature

from Ui_FindDialog import Ui_FindDialog

class FindDialog(QDialog, Ui_FindDialog):
    """
    Class documentation goes here.
    """
    findHistroyList = None
    
    options = None
    
    widget = None
    
    def __init__(self, parent = None):
        """
        Constructor
        """
        QDialog.__init__(self, parent)
        self.setupUi(self)
        
        self.comboBox.setEditable(1)
        self.findHistroyList = QStringList()

    @pyqtSignature("bool")
    def on_pushButton_find_clicked(self, checked):
        """
        Slot documentation goes here.
        """
        lineEditText = self.comboBox.lineEdit().text()
        if lineEditText == QString(""):
            return
        else:
            if self.comboBox.findText(lineEditText) == -1:
                self.comboBox.insertItem(0,lineEditText)
                self.findHistroyList.append(lineEditText)
            else:
                self.comboBox.removeItem(self.comboBox.findText(lineEditText))
                self.comboBox.insertItem(0, lineEditText)
                self.comboBox.setEditText(lineEditText)

        self.options = QTextDocument.FindFlags(0)
        
        # CaseSensitivity
        if self.checkBox_1.isChecked():
            self.options = self.options.__ior__(QTextDocument.FindCaseSensitively)

        # Backward
        if self.checkBox_3.isChecked():
            self.options = self.options.__ior__(QTextDocument.FindBackward)
            
        # WholeWords
        if self.checkBox_2.isChecked():
            self.options = self.options.__ior__(QTextDocument.FindWholeWords)

        self.widget.find(lineEditText, self.options)
        
        
    def setWidget(self, widget):
        self.widget = widget
        self.moveCursorToStart()
        
    def moveCursorToStart(self):
        cursor = self.widget.textCursor()
        selectedText = cursor.selectedText()
        cursor.movePosition(QTextCursor.Start, QTextCursor.MoveAnchor)
    
    def setFindHistroyList(self, findHistroyList):
        self.findHistroyList = findHistroyList
        self.comboBox.insertItems(0, self.findHistroyList)
    

