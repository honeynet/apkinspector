from PyQt4.QtGui import *
from PyQt4.QtCore import *


class ProgressDialog(QProgressDialog):
    step = 0
    
    def __init__(self):
        super(ProgressDialog, self).__init__()
        self.setRange(0, 1000000)
        self.setModal(1)
        self.setLabelText("Please wait a moment! Don't cancel it!")
        
    def run(self):
        while self.step <= 1000000:
            self.setValue(self.step)
            self.step += 1
             
    def setStep(self, value):
        self.step = value
        
