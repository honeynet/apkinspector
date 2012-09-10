import os
import sys
import zipfile

from startQT import SYSPATH

# delete all files and dirs in the "./temp/" dictionary
# return 0: success;
def clear():
    cmd = "rm -rf " + SYSPATH + "/temp/*"
    return os.system(cmd)

# use the dex2jar to generate the .jar file.
# Then move .jar file to the "./temp/" dictionary
# At last, unzip the .jar file to "./temp/" dictionary
# return 1: success ; return 0: fail
def dex2jar(filename):
    cmd1 = SYSPATH +"/dex2jar/dex2jar.sh " + filename
    if os.system(cmd1) !=0:
        return 0
    
#    newfilename = os.path.split(filename)[-1] + ".dex2jar.jar"
    newfilename = os.path.split(filename)[-1]
    newfilename = os.path.splitext(newfilename)[0] + "_dex2jar.jar"
    cmd2 = "mv " + os.path.dirname(filename) + "/" + newfilename + " " + SYSPATH + "/temp/"
    if os.system(cmd2) !=0:
        return 0
        
    if unzip(SYSPATH + "/temp/" + newfilename) != 0:
        return 0
    
    return 1

# unzip the .jar file
# return 0: success;
def unzip(filename):
    cmd = "unzip -o " + filename + " -d" + SYSPATH + "/temp/unzip"
    return os.system(cmd)

# decompile the apk to the Javacodes
# parameter:
#       filename: the full absolute path of the apk file
# return 1: success; return 0:fail
def decompile(filename):
    if clear() != 0:
        return 0
        
    if dex2jar(filename) != 1:
        return 0
  
#    cmd2 = SYSPATH + "/jad158e.linux.static/jad -o -r -sjava -d" + SYSPATH + "/temp/java " + SYSPATH + "/temp/unzip/**/*.class"
#    if os.system(cmd) != 0:
#        return 0
#add support for JAVA 5, use jadretro
    cmd1 = SYSPATH + "/jadretro/jadretro" + SYSPATH + "/temp/unzip/**/*.class"
    if os.system(cmd1) != 0:
        return 0
    cmd2 = SYSPATH + "/jad158e.linux.static/jad -o -r -sjava -d" + SYSPATH + "/temp/java " + SYSPATH + "/temp/unzip/**/*.class"
    if os.system(cmd2) != 0:
        return 0
 
    return 1



    
    
    
