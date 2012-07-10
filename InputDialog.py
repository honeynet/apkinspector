# -*- coding: utf-8 -*-

"""
Module implementing inputDialog.
"""
from PyQt4.QtGui import *
from PyQt4.QtCore import *
from PyQt4.QtGui import QDialog
from PyQt4.QtCore import pyqtSignature

from Ui_InputDialog import Ui_inputDialog_dalvik

class inputDialog(QDialog, Ui_inputDialog_dalvik):
    """
    Class documentation goes here.
    """
    node = None
    
    def __init__(self, node, myText):
        """
        Constructor
        """
        QDialog.__init__(self)
        self.setupUi(self)
        self.plainTextEdit_dalvik.setPlainText(myText)
        self.node = node

    
    @pyqtSignature("")
    def on_pushButton_ok_dalvik_clicked(self):
        """
        Slot documentation goes here.
        """

        newText = self.plainTextEdit_dalvik.toPlainText()
        self.node.setText(newText)
        
        
