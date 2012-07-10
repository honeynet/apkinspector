# -*- coding: utf-8 -*-

"""
Module implementing RenamingDialog.
"""

from PyQt4.QtGui import QDialog
from PyQt4.QtCore import pyqtSignature

from Ui_RenamingDialog import Ui_RenamingDialog

class RenamingDialog(QDialog, Ui_RenamingDialog):
    """
    Class documentation goes here.
    """
    newName = None
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
        self.newName = self.lineEdit_new.text()
