from PyQt4.QtGui import *
from PyQt4.QtCore import *

import Global
import JAD
import APKtool
from APKInfo import *


class MyThread(QThread):
    progress = None
    filename = None
    
    def __init__(self,progress, filename):
        super(MyThread, self).__init__()
        self.progress = progress
        self.filename = filename
        
    def run(self):
        Global.APK = APK(self.filename)
        if not Global.APK.isVaildAPK():
            self.progress.step = 599900
        else:
            Global.VM = DalvikVMFormat(Global.APK.getDex())
            Global.VMX = analysis.VMAnalysis(Global.VM)
        
            if self.progress.step <= 500000:
                self.progress.step = 500000
        
            if Global.CONFIG["Java"] == 1:
                Global.FLAG_JAD = JAD.decompile(self.filename)
            if self.progress.step <= 800000:
                self.progress.step = 800000
            
            if Global.CONFIG["Smali"] ==1 or Global.CONFIG["Manifest"] ==1:
                Global.FLAG_APKTOOL = APKtool.callAPKtool(self.filename)
            if self.progress.step <= 990000:
                self.progress.step = 990000
            
