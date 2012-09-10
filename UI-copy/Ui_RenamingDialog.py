# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file '/home/administrator/gsoc-AndroidGui/UI/RenamingDialog.ui'
#
# Created: Mon Aug 22 00:40:52 2011
#      by: PyQt4 UI code generator 4.8.4
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_RenamingDialog(object):
    def setupUi(self, RenamingDialog):
        RenamingDialog.setObjectName(_fromUtf8("RenamingDialog"))
        RenamingDialog.resize(351, 254)
        self.gridLayoutWidget = QtGui.QWidget(RenamingDialog)
        self.gridLayoutWidget.setGeometry(QtCore.QRect(30, 30, 291, 131))
        self.gridLayoutWidget.setObjectName(_fromUtf8("gridLayoutWidget"))
        self.gridLayout = QtGui.QGridLayout(self.gridLayoutWidget)
        self.gridLayout.setMargin(0)
        self.gridLayout.setObjectName(_fromUtf8("gridLayout"))
        self.lineEdit_new = QtGui.QLineEdit(self.gridLayoutWidget)
        self.lineEdit_new.setObjectName(_fromUtf8("lineEdit_new"))
        self.gridLayout.addWidget(self.lineEdit_new, 3, 0, 1, 1)
        self.lineEdit_old = QtGui.QLineEdit(self.gridLayoutWidget)
        self.lineEdit_old.setEnabled(False)
        self.lineEdit_old.setObjectName(_fromUtf8("lineEdit_old"))
        self.gridLayout.addWidget(self.lineEdit_old, 1, 0, 1, 1)
        self.label = QtGui.QLabel(self.gridLayoutWidget)
        self.label.setObjectName(_fromUtf8("label"))
        self.gridLayout.addWidget(self.label, 0, 0, 1, 1)
        self.label_2 = QtGui.QLabel(self.gridLayoutWidget)
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.gridLayout.addWidget(self.label_2, 2, 0, 1, 1)
        self.pushButton_ok = QtGui.QPushButton(RenamingDialog)
        self.pushButton_ok.setGeometry(QtCore.QRect(50, 190, 85, 27))
        self.pushButton_ok.setObjectName(_fromUtf8("pushButton_ok"))
        self.pushButton_cancel = QtGui.QPushButton(RenamingDialog)
        self.pushButton_cancel.setGeometry(QtCore.QRect(180, 190, 85, 27))
        self.pushButton_cancel.setObjectName(_fromUtf8("pushButton_cancel"))

        self.retranslateUi(RenamingDialog)
        QtCore.QObject.connect(self.pushButton_cancel, QtCore.SIGNAL(_fromUtf8("clicked(bool)")), RenamingDialog.close)
        QtCore.QObject.connect(self.pushButton_ok, QtCore.SIGNAL(_fromUtf8("clicked(bool)")), RenamingDialog.close)
        QtCore.QMetaObject.connectSlotsByName(RenamingDialog)

    def retranslateUi(self, RenamingDialog):
        RenamingDialog.setWindowTitle(QtGui.QApplication.translate("RenamingDialog", "Dialog", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("RenamingDialog", "Rename", None, QtGui.QApplication.UnicodeUTF8))
        self.label_2.setText(QtGui.QApplication.translate("RenamingDialog", "To", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_ok.setText(QtGui.QApplication.translate("RenamingDialog", "Ok", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_cancel.setText(QtGui.QApplication.translate("RenamingDialog", "Cancel", None, QtGui.QApplication.UnicodeUTF8))


if __name__ == "__main__":
    import sys
    app = QtGui.QApplication(sys.argv)
    RenamingDialog = QtGui.QDialog()
    ui = Ui_RenamingDialog()
    ui.setupUi(RenamingDialog)
    RenamingDialog.show()
    sys.exit(app.exec_())

