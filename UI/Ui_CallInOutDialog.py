# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file '/home/administrator/gsoc-AndroidGui/UI/CallInOutDialog.ui'
#
# Created: Mon Aug 22 00:40:54 2011
#      by: PyQt4 UI code generator 4.8.4
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_CallInOutDialog(object):
    def setupUi(self, CallInOutDialog):
        CallInOutDialog.setObjectName(_fromUtf8("CallInOutDialog"))
        CallInOutDialog.resize(601, 332)
        self.label = QtGui.QLabel(CallInOutDialog)
        self.label.setGeometry(QtCore.QRect(120, 20, 351, 17))
        self.label.setObjectName(_fromUtf8("label"))
        self.label_5 = QtGui.QLabel(CallInOutDialog)
        self.label_5.setGeometry(QtCore.QRect(20, 50, 531, 17))
        self.label_5.setObjectName(_fromUtf8("label_5"))
        self.widget = QtGui.QWidget(CallInOutDialog)
        self.widget.setGeometry(QtCore.QRect(20, 90, 551, 29))
        self.widget.setObjectName(_fromUtf8("widget"))
        self.horizontalLayout = QtGui.QHBoxLayout(self.widget)
        self.horizontalLayout.setMargin(0)
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.label_2 = QtGui.QLabel(self.widget)
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.horizontalLayout.addWidget(self.label_2)
        self.lineEdit_class = QtGui.QLineEdit(self.widget)
        self.lineEdit_class.setObjectName(_fromUtf8("lineEdit_class"))
        self.horizontalLayout.addWidget(self.lineEdit_class)
        self.widget1 = QtGui.QWidget(CallInOutDialog)
        self.widget1.setGeometry(QtCore.QRect(20, 140, 551, 29))
        self.widget1.setObjectName(_fromUtf8("widget1"))
        self.horizontalLayout_2 = QtGui.QHBoxLayout(self.widget1)
        self.horizontalLayout_2.setMargin(0)
        self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
        self.label_3 = QtGui.QLabel(self.widget1)
        self.label_3.setObjectName(_fromUtf8("label_3"))
        self.horizontalLayout_2.addWidget(self.label_3)
        self.lineEdit_method = QtGui.QLineEdit(self.widget1)
        self.lineEdit_method.setObjectName(_fromUtf8("lineEdit_method"))
        self.horizontalLayout_2.addWidget(self.lineEdit_method)
        self.widget2 = QtGui.QWidget(CallInOutDialog)
        self.widget2.setGeometry(QtCore.QRect(20, 190, 551, 29))
        self.widget2.setObjectName(_fromUtf8("widget2"))
        self.horizontalLayout_3 = QtGui.QHBoxLayout(self.widget2)
        self.horizontalLayout_3.setMargin(0)
        self.horizontalLayout_3.setObjectName(_fromUtf8("horizontalLayout_3"))
        self.label_4 = QtGui.QLabel(self.widget2)
        self.label_4.setObjectName(_fromUtf8("label_4"))
        self.horizontalLayout_3.addWidget(self.label_4)
        self.lineEdit_descriptor = QtGui.QLineEdit(self.widget2)
        self.lineEdit_descriptor.setObjectName(_fromUtf8("lineEdit_descriptor"))
        self.horizontalLayout_3.addWidget(self.lineEdit_descriptor)
        self.widget3 = QtGui.QWidget(CallInOutDialog)
        self.widget3.setGeometry(QtCore.QRect(200, 240, 231, 24))
        self.widget3.setObjectName(_fromUtf8("widget3"))
        self.horizontalLayout_4 = QtGui.QHBoxLayout(self.widget3)
        self.horizontalLayout_4.setMargin(0)
        self.horizontalLayout_4.setObjectName(_fromUtf8("horizontalLayout_4"))
        self.checkBox_in = QtGui.QCheckBox(self.widget3)
        self.checkBox_in.setChecked(True)
        self.checkBox_in.setObjectName(_fromUtf8("checkBox_in"))
        self.horizontalLayout_4.addWidget(self.checkBox_in)
        self.checkBox_out = QtGui.QCheckBox(self.widget3)
        self.checkBox_out.setChecked(True)
        self.checkBox_out.setObjectName(_fromUtf8("checkBox_out"))
        self.horizontalLayout_4.addWidget(self.checkBox_out)
        self.widget4 = QtGui.QWidget(CallInOutDialog)
        self.widget4.setGeometry(QtCore.QRect(50, 290, 461, 29))
        self.widget4.setObjectName(_fromUtf8("widget4"))
        self.horizontalLayout_5 = QtGui.QHBoxLayout(self.widget4)
        self.horizontalLayout_5.setMargin(0)
        self.horizontalLayout_5.setObjectName(_fromUtf8("horizontalLayout_5"))
        self.pushButton_ok = QtGui.QPushButton(self.widget4)
        self.pushButton_ok.setMaximumSize(QtCore.QSize(85, 27))
        self.pushButton_ok.setObjectName(_fromUtf8("pushButton_ok"))
        self.horizontalLayout_5.addWidget(self.pushButton_ok)
        self.pushButton_cancel = QtGui.QPushButton(self.widget4)
        self.pushButton_cancel.setMaximumSize(QtCore.QSize(85, 27))
        self.pushButton_cancel.setObjectName(_fromUtf8("pushButton_cancel"))
        self.horizontalLayout_5.addWidget(self.pushButton_cancel)

        self.retranslateUi(CallInOutDialog)
        QtCore.QObject.connect(self.pushButton_cancel, QtCore.SIGNAL(_fromUtf8("clicked(bool)")), CallInOutDialog.close)
        QtCore.QObject.connect(self.pushButton_ok, QtCore.SIGNAL(_fromUtf8("clicked(bool)")), CallInOutDialog.close)
        QtCore.QMetaObject.connectSlotsByName(CallInOutDialog)

    def retranslateUi(self, CallInOutDialog):
        CallInOutDialog.setWindowTitle(QtGui.QApplication.translate("CallInOutDialog", "Call in/out Dialog", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("CallInOutDialog", "Please input the method to get the call in/out methods", None, QtGui.QApplication.UnicodeUTF8))
        self.label_5.setText(QtGui.QApplication.translate("CallInOutDialog", "Example:Lcom/dlp/SMSReplicatorSecret/ShadyDB; (Landroid/content/Context;)V,<init>", None, QtGui.QApplication.UnicodeUTF8))
        self.label_2.setText(QtGui.QApplication.translate("CallInOutDialog", "Class Name:  ", None, QtGui.QApplication.UnicodeUTF8))
        self.lineEdit_class.setText(QtGui.QApplication.translate("CallInOutDialog", "Lcom/dlp/SMSReplicatorSecret/ShadyDB;", None, QtGui.QApplication.UnicodeUTF8))
        self.label_3.setText(QtGui.QApplication.translate("CallInOutDialog", "Method Name:", None, QtGui.QApplication.UnicodeUTF8))
        self.lineEdit_method.setText(QtGui.QApplication.translate("CallInOutDialog", "<init>", None, QtGui.QApplication.UnicodeUTF8))
        self.label_4.setText(QtGui.QApplication.translate("CallInOutDialog", "Descriptor:    ", None, QtGui.QApplication.UnicodeUTF8))
        self.lineEdit_descriptor.setText(QtGui.QApplication.translate("CallInOutDialog", "(Landroid/content/Context;)V", None, QtGui.QApplication.UnicodeUTF8))
        self.checkBox_in.setText(QtGui.QApplication.translate("CallInOutDialog", "Call in", None, QtGui.QApplication.UnicodeUTF8))
        self.checkBox_out.setText(QtGui.QApplication.translate("CallInOutDialog", "Call out", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_ok.setText(QtGui.QApplication.translate("CallInOutDialog", "OK", None, QtGui.QApplication.UnicodeUTF8))
        self.pushButton_cancel.setText(QtGui.QApplication.translate("CallInOutDialog", "Cancel", None, QtGui.QApplication.UnicodeUTF8))


if __name__ == "__main__":
    import sys
    app = QtGui.QApplication(sys.argv)
    CallInOutDialog = QtGui.QDialog()
    ui = Ui_CallInOutDialog()
    ui.setupUi(CallInOutDialog)
    CallInOutDialog.show()
    sys.exit(app.exec_())

