# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file '/home/administrator/gsoc-AndroidGui/UI/InputDialog.ui'
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

class Ui_inputDialog_dalvik(object):
    def setupUi(self, inputDialog_dalvik):
        inputDialog_dalvik.setObjectName(_fromUtf8("inputDialog_dalvik"))
        inputDialog_dalvik.resize(612, 538)
        inputDialog_dalvik.setContextMenuPolicy(QtCore.Qt.NoContextMenu)
        inputDialog_dalvik.setSizeGripEnabled(False)
        inputDialog_dalvik.setModal(False)
        self.gridLayout_2 = QtGui.QGridLayout(inputDialog_dalvik)
        self.gridLayout_2.setObjectName(_fromUtf8("gridLayout_2"))
        self.gridLayout = QtGui.QGridLayout()
        self.gridLayout.setObjectName(_fromUtf8("gridLayout"))
        self.plainTextEdit_dalvik = QtGui.QPlainTextEdit(inputDialog_dalvik)
        self.plainTextEdit_dalvik.setLineWrapMode(QtGui.QPlainTextEdit.NoWrap)
        self.plainTextEdit_dalvik.setObjectName(_fromUtf8("plainTextEdit_dalvik"))
        self.gridLayout.addWidget(self.plainTextEdit_dalvik, 0, 0, 1, 1)
        self.horizontalLayout_dalvik = QtGui.QHBoxLayout()
        self.horizontalLayout_dalvik.setObjectName(_fromUtf8("horizontalLayout_dalvik"))
        self.pushButton_ok_dalvik = QtGui.QPushButton(inputDialog_dalvik)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.pushButton_ok_dalvik.sizePolicy().hasHeightForWidth())
        self.pushButton_ok_dalvik.setSizePolicy(sizePolicy)
        self.pushButton_ok_dalvik.setObjectName(_fromUtf8("pushButton_ok_dalvik"))
        self.horizontalLayout_dalvik.addWidget(self.pushButton_ok_dalvik)
        self.pushButton_cancel_dalvik = QtGui.QPushButton(inputDialog_dalvik)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.pushButton_cancel_dalvik.sizePolicy().hasHeightForWidth())
        self.pushButton_cancel_dalvik.setSizePolicy(sizePolicy)
        self.pushButton_cancel_dalvik.setObjectName(_fromUtf8("pushButton_cancel_dalvik"))
        self.horizontalLayout_dalvik.addWidget(self.pushButton_cancel_dalvik)
        self.gridLayout.addLayout(self.horizontalLayout_dalvik, 1, 0, 1, 1)
        self.gridLayout_2.addLayout(self.gridLayout, 0, 0, 1, 1)

        self.retranslateUi(inputDialog_dalvik)
        QtCore.QObject.connect(self.pushButton_ok_dalvik, QtCore.SIGNAL(_fromUtf8("clicked()")), inputDialog_dalvik.close)
        QtCore.QObject.connect(self.pushButton_cancel_dalvik, QtCore.SIGNAL(_fromUtf8("clicked()")), inputDialog_dalvik.close)
        QtCore.QMetaObject.connectSlotsByName(inputDialog_dalvik)

    def retranslateUi(self, inputDialog_dalvik):
        inputDialog_dalvik.setWindowTitle(QtGui.QApplication.translate("inputDialog_dalvik", "Modify the dalvik code", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_ok_dalvik.setText(QtGui.QApplication.translate("inputDialog_dalvik", "OK", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_cancel_dalvik.setText(QtGui.QApplication.translate("inputDialog_dalvik", "Cancel", None, QtGui.QApplication.UnicodeUTF8))


if __name__ == "__main__":
    import sys
    app = QtGui.QApplication(sys.argv)
    inputDialog_dalvik = QtGui.QDialog()
    ui = Ui_inputDialog_dalvik()
    ui.setupUi(inputDialog_dalvik)
    inputDialog_dalvik.show()
    sys.exit(app.exec_())

