from PyQt4.QtGui import *
from PyQt4.QtCore import *

class newMessageBox(QMessageBox):

    
    def __init__(self, parent = None):
        QMessageBox.__init__(self, parent)
        
    def resizeEvent(self, resizeEvent):
        self.setFixedSize(500, 150)

