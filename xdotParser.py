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

from APKInfo import *
from GetMethods import *

from Graph import *
from GetMethods import *
from CallInOut import *

class XDot:
    method = None
    vmx = None
    buff = None
    xdot = None
  
    
    def __init__(self, method, vm, vmx):
        self.method = method
        self.vmx = vmx
        self.buff = ""
    
    def method2xdot(self):
        import pydot
        self.buff = "digraph code {\n"
        self.buff += "graph [bgcolor=white];\n"
        self.buff += "node [color=red, style=filled shape=box fontname=\"Courier\" fontsize=\"8\"];\n"
        self.buff += "splines=ortho"
        self.buff += bytecode.method2dot(self.vmx.get_method(self.method))
        self.buff += "}"
        d = pydot.graph_from_dot_data(self.buff)
        if d:
            self.xdot = d.create_xdot()
            print "method2xdot\n"
#            print self.buff
            file = open('method2dot.txt','a')
            file.write("%s\n" % self.xdot)
            file.close

# yuan build the call graph
    def call2xdot(self, methodInvokeList, allmethod):
        import pydot
     
        callInOut = CallInOut(methodInvokeList)  
        className = self.method.get_class_name()
        methodName = self.method.get_name()
        descriptor = self.method.get_descriptor()
        callMethod = className + " " + descriptor + "," + methodName
        callInList= callInOut.searchCallIn(callMethod)
        callOutList = callInOut.searchCallOut(callMethod)
        
        Dir = callInOut.invokeDir2
        callList = []
      
        label = ""
   
        self.buff = "digraph code {\n"
        self.buff += "graph [bgcolor=white];\n"
        self.buff += "node [color=lightgray, style=filled shape=box fontname=\"Courier\" fontsize=\"8\"];\n"
        self.buff += "splines=ortho"
        i = 0
        j = 100
        label2name = {}
        """
        for I in allmethod:
            i += 1
            Im = I.get_class_name() + " " + I.get_descriptor() +"," + I.get_name()

            node_name = "%s" % i
            label = Im

            label2name[label] = node_name
 
            self.buff += "\"%s\" [color=\"lightgray\", label=\"%s\"]\n" % (node_name, label)
        print "total method count = %s" % i
        """
        
        for I in allmethod:
              Im = I.get_class_name() + " " + I.get_descriptor() +"," + I.get_name()
              if Im not in Dir.keys():
                   continue
              callList = Dir[Im]
              lenth = len(callList)
              for l in range (0, lenth ):
                   col = "blue"

                   if not label2name.has_key(Im):
                     node_from = "node%s" % i
                     label2name[Im] = node_from
                     i += 1
                   else:
                     node_from = label2name[Im]

                   if not label2name.has_key(callList[l]):
                     node_to = "node%s" % i
                     label2name[callList[l]] = node_to
                     i += 1
                   else:
                     node_to = label2name[callList[l]]

                   try:
#                     self.buff += "\"%s\" -> \"%s\" [color=\"%s\"];\n " % (Im, callList[l],col ) 
                      self.buff += "\"%s\" -> \"%s\" [color=\"%s\"];\n " % (node_from, node_to,col ) 
                   except:
                     print "error"
        """
        for I in callInList:
            i += 1
            label = I
            self.buff += "\"%s\" [color=\"lightgray\", label=\"%s\"]\n" % (i, label)
            self.buff += "\"%s\" -> \"%s\";\n " % (I, callMethod )
            
        for O in callOutList:
            j += 1
            label = O
            self.buff += "\"%s\" [color=\"lightgray\", label=\"%s\"]\n" % (j, label)
            self.buff += "\"%s\" -> \"%s\";\n " % (callMethod, O )    
  #      for I in callInList:


  #      for O in callOutList:
  
        """

    
        self.buff += "}"
        file = open('callbuff.txt','a')
        file.write("%s\n" % self.buff)
        file.close                
        d = pydot.graph_from_dot_data(self.buff)
        if d:
            self.xdot = d.create_xdot()
#            print "xdot\n\n"
#            print self.xdot
#            print "call2xdot\n"
  #          print self.buff
            file = open('call2dot.txt','a')
            file.write("%s\n" % self.xdot)
            file.close
            d.write_svg("3.svg")


    def printxdot(self):
        print self.xdot
        
    def transform(self, point_y, pageHeight):
        return pageHeight - point_y
    
    def parse(self):
        pagesize = [0, 0]
        nodeList = []
        linkList = []

        
        start = self.xdot.index("graph [bb=\"0,0,")+15
        end = self.xdot[start:].index("\"") + start
        [pagesize[0], pagesize[1]] = self.xdot[start:end].split(",")
        if pagesize == ['0', '0']:
            return  [[0, 0], nodeList, linkList]
            
        # turn the String type to the Float type
        [pagesize[0], pagesize[1]] = [string.atof(pagesize[0]), string.atof(pagesize[1])]

        # In parseStr, we can get all information about each node and link
        parseStr = self.xdot[self.xdot[end:].index("];")+ end + 2 : len(self.xdot)-2]
        
        
        parseStr = parseStr.replace("\t", "")
        parseStr = parseStr.replace("\n", "")
        parseStr = parseStr.replace("\\l", "\n")
        
        parselist = parseStr.split("];")
        file = open("parselistmethod.txt",'w')
        file.write("%s" % parselist)
        file.close()
        for i in parselist:
            if i.find("->") == -1 and i != '':

                start = i.index("label=") + 7
                end = i[start:].index("\"") + start
                # label is the content of the node
                label = i[start : end]
                label = label.replace("\\", "")
                
                start = i[end:].index("P 4 ") + end + 4
                end = i[start:].index("\"") + start
                
                points = i[start:end]
                points.strip()
                points = points.split(" ")
                width = string.atof(points[0]) - string.atof(points[2])
                height = string.atof(points[3]) - string.atof(points[5])
                point_x = string.atof(points[2])
                point_y = self.transform(string.atof(points[3]), pagesize[1])
                # this point is the left-top point
                point = [point_x, point_y]
                
                node = Node(point[0], point[1], width, height)
                node.setText(label)
                node.setHint(label)
                nodeList.append(node)

            elif i != '':
                i = i.replace("\\", "")
                
                color = i[i.index("color=")+6:i.index(", pos")]
                path = i[i.index("pos=\"e,")+7:i.index("\", _draw_=")]
                path = path.replace(",", " ")
                path = path.split(" ")
                path = path[2:]
                
                
                for j in range(0, len(path)):
                    if j % 2 == 1:
                        path[j] = self.transform(string.atof(path[j]), pagesize[1])
                    else:
                        path[j] = string.atof(path[j])
                    
                arrow = i[i.rindex("P 3 ")+4: i.rindex(" \"")]
                arrow = arrow.split(" ")
                
                for j in range(0, len(arrow)):
                    if j % 2 ==1:
                        arrow[j] = self.transform(string.atof(arrow[j]), pagesize[1])
                    else:
                        arrow[j] = string.atof(arrow[j])
                

                link = Link()
                link.setColor(color)
                link.drawLine(path)
                link.drawArrow(arrow)
                linkList.append(link)
      
        return [pagesize, nodeList, linkList]


    def parsecall(self):
        pagesize = [0, 0]
        nodeList = []
        linkList = []

        
        start = self.xdot.index("graph [bb=\"0,0,")+15
        end = self.xdot[start:].index("\"") + start
        [pagesize[0], pagesize[1]] = self.xdot[start:end].split(",")
        if pagesize == ['0', '0']:
            return  [[0, 0], nodeList, linkList]
            
        # turn the String type to the Float type
        [pagesize[0], pagesize[1]] = [string.atof(pagesize[0]), string.atof(pagesize[1])]

        # In parseStr, we can get all information about each node and link
        parseStr = self.xdot[self.xdot[end:].index("];")+ end + 2 : len(self.xdot)-2]
        
        
        parseStr = parseStr.replace("\t", "")
        parseStr = parseStr.replace("\n", "")
        parseStr = parseStr.replace("\\l", "\n")
        
        parselist = parseStr.split("];")
        file = open("parselistcall.txt",'w')
        file.write("%s" % parselist)
        file.close()
        for i in parselist:
            if i.find("->") == -1 and i != '':
                try:
                  start = i.index("label=") + 7
 #               start = i.find("label=") + 7
                  end = i[start:].index("\"") + start
                # label is the content of the node
                  label = i[start : end]
                  label = label.replace("\\", "")
                except:
                   label = "123"
                   end = 0
                
                try:

                  start = i[end:].index("P 4 ") + end + 4
                  end = i[start:].index("\"") + start
                
                
                  points = i[start:end]
                  points.strip()
                  points = points.split(" ")
                  width = string.atof(points[0]) - string.atof(points[2])
                  height = string.atof(points[3]) - string.atof(points[5])
                  point_x = string.atof(points[2])
                  point_y = self.transform(string.atof(points[3]), pagesize[1])
                # this point is the left-top point
                  point = [point_x, point_y]
                except:
                  print "Error = %s" % i
                  point = [0.0, 0.0]
                  width = 50.0
                  height = 40.0
                
                node = Node(point[0], point[1], width, height)
                node.setText_call(label)
                node.setHint(label)
                nodeList.append(node)
            elif i != '':
                i = i.replace("\\", "")
                
                color = i[i.index("color=")+6:i.index(", pos")]



                path = i[i.index("pos=\"e,")+7:i.index("\", _draw_=")]
                path = path.replace(",", " ")
                path = path.split(" ")
                path = path[2:]
                
                
                for j in range(0, len(path)):
                    if j % 2 == 1:
                        path[j] = self.transform(string.atof(path[j]), pagesize[1])
                    else:
                        path[j] = string.atof(path[j])
                try :               
                    arrow = i[i.rindex("P 3 ")+4: i.rindex(" \"")]
                    arrow = arrow.split(" ")
                except:
                    print 123

                for j in range(0, len(arrow)):
                    if j % 2 ==1:
                        arrow[j] = self.transform(string.atof(arrow[j]), pagesize[1])
                    else:
                        arrow[j] = string.atof(arrow[j])
            

                link = Link()
                link.setColor(color)
                link.drawLine(path)
                link.drawArrow(arrow)
                linkList.append(link)
      
        return [pagesize, nodeList, linkList]

    
