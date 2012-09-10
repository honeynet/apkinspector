# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file '/home/administrator/gsoc-AndroidGui/UI/FindDialog.ui'
#
# Created: Mon Aug 22 00:40:53 2011
#      by: PyQt4 UI code generator 4.8.4
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_FindDialog(object):
    def setupUi(self, FindDialog):
        FindDialog.setObjectName(_fromUtf8("FindDialog"))
        FindDialog.resize(400, 300)
        FindDialog.setSizeGripEnabled(False)
        FindDialog.setModal(False)
        self.label = QtGui.QLabel(FindDialog)
        self.label.setGeometry(QtCore.QRect(30, 30, 64, 17))
        self.label.setObjectName(_fromUtf8("label"))
        self.checkBox_1 = QtGui.QCheckBox(FindDialog)
        self.checkBox_1.setGeometry(QtCore.QRect(30, 100, 94, 22))
        self.checkBox_1.setObjectName(_fromUtf8("checkBox_1"))
        self.checkBox_2 = QtGui.QCheckBox(FindDialog)
        self.checkBox_2.setGeometry(QtCore.QRect(30, 130, 169, 22))
        self.checkBox_2.setObjectName(_fromUtf8("checkBox_2"))
        self.checkBox_3 = QtGui.QCheckBox(FindDialog)
        self.checkBox_3.setGeometry(QtCore.QRect(30, 160, 134, 22))
        self.checkBox_3.setChecked(False)
        self.checkBox_3.setObjectName(_fromUtf8("checkBox_3"))
        self.checkBox_4 = QtGui.QCheckBox(FindDialog)
        self.checkBox_4.setGeometry(QtCore.QRect(30, 190, 106, 22))
        self.checkBox_4.setChecked(True)
        self.checkBox_4.setTristate(False)
        self.checkBox_4.setObjectName(_fromUtf8("checkBox_4"))
        self.comboBox = QtGui.QComboBox(FindDialog)
        self.comboBox.setGeometry(QtCore.QRect(30, 50, 341, 31))
        self.comboBox.setEditable(True)
        self.comboBox.setObjectName(_fromUtf8("comboBox"))
        self.pushButton_close = QtGui.QPushButton(FindDialog)
        self.pushButton_close.setGeometry(QtCore.QRect(230, 240, 85, 27))
        self.pushButton_close.setObjectName(_fromUtf8("pushButton_close"))
        self.pushButton_find = QtGui.QPushButton(FindDialog)
        self.pushButton_find.setGeometry(QtCore.QRect(80, 240, 85, 27))
        self.pushButton_find.setObjectName(_fromUtf8("pushButton_find"))

        self.retranslateUi(FindDialog)
        QtCore.QObject.connect(self.pushButton_close, QtCore.SIGNAL(_fromUtf8("clicked(bool)")), FindDialog.close)
        QtCore.QMetaObject.connectSlotsByName(FindDialog)

    def retranslateUi(self, FindDialog):
        FindDialog.setWindowTitle(QtGui.QApplication.translate("FindDialog", "Find", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("FindDialog", "Search for", None, QtGui.QApplication.UnicodeUTF8))
        self.checkBox_1.setText(QtGui.QApplication.translate("FindDialog", "Match case", None, QtGui.QApplication.UnicodeUTF8))
        self.checkBox_2.setText(QtGui.QApplication.translate("FindDialog", "Match entire word only", None, QtGui.QApplication.UnicodeUTF8))
        self.checkBox_3.setText(QtGui.QApplication.translate("FindDialog", "Search backwards", None, QtGui.QApplication.UnicodeUTF8))
        self.checkBox_4.setText(QtGui.QApplication.translate("FindDialog", "Wrap around", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_close.setText(QtGui.QApplication.translate("FindDialog", "Close", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_find.setText(QtGui.QApplication.translate("FindDialog", "Find", None, QtGui.QApplication.UnicodeUTF8))


if __name__ == "__main__":
    import sys
    app = QtGui.QApplication(sys.argv)
    FindDialog = QtGui.QDialog()
    ui = Ui_FindDialog()
    ui.setupUi(FindDialog)
    FindDialog.show()
    sys.exit(app.exec_())

