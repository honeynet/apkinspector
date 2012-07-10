# -*- coding: utf-8 -*-

"""
Module implementing CallInOutDialog.
"""

from PyQt4.QtGui import QDialog
from PyQt4.QtCore import pyqtSignature

from Ui_CallInOutDialog import Ui_CallInOutDialog

class CallInOutDialog(QDialog, Ui_CallInOutDialog):

    callInFlag = 0
    callOutFlag = 0
    callMethod = None
    
    def __init__(self, parent = None):
        """
        Constructor
        """
        QDialog.__init__(self, parent)
        self.setupUi(self)
    
    @pyqtSignature("bool")
    def on_pushButton_ok_clicked(self, checked):
        """
        Slot documentation goes here.
        """
        className = str(self.lineEdit_class.text())
        methodName = str(self.lineEdit_method.text())
        descriptor = str(self.lineEdit_descriptor.text())
        self.callInFlag = self.checkBox_in.isChecked()
        self.callOutFlag = self.checkBox_out.isChecked()
        self.callMethod = className + " " + descriptor + "," + methodName
        
