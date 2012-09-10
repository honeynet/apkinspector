import re
import copy
import sys


class CallInOut:    
    invokeDir={}        # invokeDir:{the invoked method ---> the invoking method}
    invokeDir2 = {}     # invokeDir2:{the invoking method ---> the invoked method}

    def __init__(self, methodInvokeList):
        self.preprocess(methodInvokeList)
    
    def preprocess(self, methodInvokeList):
        """
            This function is to pre-process the invoke relations.
            @param methodInvokeList: the list of method ---> method
        """
        pattern="(.*) ---> (.*)"
    
        for line in methodInvokeList:
            li=re.search(pattern,line).groups()
            # if the recursion exists, the detect loop will not be end
            if li[0] == li[1]:
                continue		
           
            m0 = li[1].split("^")[0]
            m1 = li[1].split("^")[1]
            
            if m0 not in list(self.invokeDir.keys()):
                self.invokeDir[m0]=[li[0]+"^" +m1]
            else:
                if li[0] not in self.invokeDir[m0]:
                    self.invokeDir[m0].append(li[0]+"^" +m1)

            if li[0] not in list(self.invokeDir2.keys()):
                self.invokeDir2[li[0]]=[li[1]]
            else:
                if li[1] not in self.invokeDir2[li[0]]:
                    self.invokeDir2[li[0]].append(li[1])
        file = open('invokedir.txt','a')
        file.write("%s\n" % self.invokeDir)
        file.close
        file = open('invokedir2.txt','a')
        file.write("%s\n" % self.invokeDir2)
        file.close
 # yuan build the call graph
    def callTree(self, method):
         import Global
         mcalltree = []
#         self.firstmethod = Global.FM
         self.endmethod = Global.endmethod
         branch = self.searchCallOut(method)
         mcalltree.append(method)
         mcalltree.append(" ---> ")
         mcalltree.append(branch)
         i = 0
         while (i < 10):
              branch = self.searchCallOut(branch)
              mcalltree.append(" ---> ")
              mcalltree.append(branch)
              i += 1

         return mcalltree



    def searchCallOut(self, method):
        """
            This function is to search the "call out" methods
        """
        callOutIdList = []
        if method not in self.invokeDir2.keys():
            return []
        else:
            callOutIdList = self.invokeDir2[method]        
        return callOutIdList
 
              
    def searchCallIn(self, method):
        """
            This function is to search the "call in" methods
        """
        callInIdList = []
        if method not in self.invokeDir.keys():
            return []
        else:
            callInIdList =  self.invokeDir[method]        
        return callInIdList
