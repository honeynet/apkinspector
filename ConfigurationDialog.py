# -*- coding: utf-8 -*-

"""
Module implementing ConfigurationDialog.
"""

from PyQt4.QtGui import QDialog
from PyQt4.QtCore import pyqtSignature

from Ui_ConfigurationDialog import Ui_ConfigurationDialog



class ConfigurationDialog(QDialog, Ui_ConfigurationDialog):

    def __init__(self, parent = None):
        """
        Constructor
        """
        QDialog.__init__(self, parent)
        self.setupUi(self)
        
        import Global
        
        if Global.CONFIG["CFG"] == 1:
            self.checkBox_cfg.setChecked(1)
        else:
            self.checkBox_cfg.setChecked(0)
            
        if Global.CONFIG["Dalvik"] == 1:
            self.checkBox_dalvik.setChecked(1)
        else:
            self.checkBox_dalvik.setChecked(0)

        if Global.CONFIG["Java"] == 1:
            self.checkBox_javacode.setChecked(1)
        else:
            self.checkBox_javacode.setChecked(0)


        if Global.CONFIG["Bytecode"] == 1:
            self.checkBox_bytecode.setChecked(1)
        else:
            self.checkBox_bytecode.setChecked(0)
            
            
        if Global.CONFIG["Smali"] == 1:
            self.checkBox_smalicode.setChecked(1)
        else:
            self.checkBox_smalicode.setChecked(0)


        if Global.CONFIG["CallIn"] == 1:
            self.checkBox_callin.setChecked(1)
        else:
            self.checkBox_callin.setChecked(0)
 
 
        if Global.CONFIG["CallOut"] == 1:
            self.checkBox_callout.setChecked(1)
        else:
            self.checkBox_callout.setChecked(0)
            
        if Global.CONFIG["Permission"] == 1:
            self.checkBox_permission.setChecked(1)
        else:
            self.checkBox_permission.setChecked(0)
            
            
        if Global.CONFIG["Manifest"] == 1:
            self.checkBox_manifest.setChecked(1)
        else:
            self.checkBox_manifest.setChecked(0)
    
    @pyqtSignature("bool")
    def on_pushButton_ok_clicked(self, checked):
        """
        Slot documentation goes here.
        """
        import Global

        if self.checkBox_cfg.isChecked():
            Global.CONFIG["CFG"] = 1
        else:
            Global.CONFIG["CFG"] = 0
            
        if self.checkBox_dalvik.isChecked():
            Global.CONFIG["Dalvik"] = 1
        else:
            Global.CONFIG["Dalvik"] = 0
            
        if self.checkBox_javacode.isChecked():
            Global.CONFIG["Java"] = 1
        else:
            Global.CONFIG["Java"] = 0

        if self.checkBox_bytecode.isChecked():
            Global.CONFIG["Bytecode"] = 1
        else:
            Global.CONFIG["Bytecode"] = 0            
            
        if self.checkBox_smalicode.isChecked():
            Global.CONFIG["Smali"] = 1
        else:
            Global.CONFIG["Smali"] = 0

        if self.checkBox_callin.isChecked():
            Global.CONFIG["CallIn"] = 1
        else:
            Global.CONFIG["CallIn"] = 0 
 
        if self.checkBox_callout.isChecked():
            Global.CONFIG["CallOut"] = 1
        else:
            Global.CONFIG["CallOut"] = 0
            
        if self.checkBox_permission.isChecked():
            Global.CONFIG["Permission"] = 1
        else:
            Global.CONFIG["Permission"] = 0            
            
        if self.checkBox_manifest.isChecked():
            Global.CONFIG["Manifest"] = 1
        else:
            Global.CONFIG["Manifest"] = 0
