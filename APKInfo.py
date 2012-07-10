import sys

if sys.path[0] == "":
	sys.path.append(sys.path[1]+"/androguard/")
	PATH_INSTALL = sys.path[1]+"/androguard"
else:
	sys.path.append(sys.path[0]+"/androguard/")
	PATH_INSTALL = sys.path[0]+"/androguard"

sys.path.append(PATH_INSTALL + "./")
sys.path.append(PATH_INSTALL + "/core")
sys.path.append(PATH_INSTALL + "/core/bytecodes")
sys.path.append(PATH_INSTALL + "/core/predicates")
sys.path.append(PATH_INSTALL + "/core/analysis")
sys.path.append(PATH_INSTALL + "/core/vm")
sys.path.append(PATH_INSTALL + "/core/wm")
sys.path.append(PATH_INSTALL + "/core/protection")
sys.path.append(PATH_INSTALL + "/classification")
 
import androguard, analysis, androlyze
import bytecode
from dvm import *

class APK:
    filename = None
    apk = None
    
    def __init__(self, filename):
        self.filename = filename
        self.apk = androlyze.APK(filename)

    def isVaildAPK(self):
        return self.apk.is_valid_APK()
    
    def getFilename(self):
        return str(self.apk.get_filename())
        
    def getVersionCode(self):
        return str(self.apk.get_androidversion_code())
    
    def getVersionName(self):
        return str(self.apk.get_androidversion_name())
        
    def getPackage(self):
        return str(self.apk.get_package())
    
    def getReceivers(self):
        retStr = ""
        receivers = self.apk.get_receivers()
        for i in receivers:
            retStr += i +"\n"
        if retStr != "":
            retStr = retStr[:-1]
        return (retStr, len(receivers))
    
    def getServices(self):
        retStr = ""
        services = self.apk.get_services()
        for i in services:
            retStr += i +"\n"
        if retStr != "":
            retStr = retStr[:-1]
        return (retStr, len(services))        

    def getPermissions(self):
        retStr = ""
        permissions = self.apk.get_permissions()
        for i in permissions:
            retStr += i +"\n"
        if retStr != "":
            retStr = retStr[:-1]
        return (retStr, len(permissions))
        
    def getDex(self):
        return self.apk.get_dex()
    
