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
        self.buff += "node [color=lightgray, style=filled shape=box fontname=\"Courier\" fontsize=\"8\"];\n"
        self.buff += "splines=ortho"
        self.buff += bytecode.method2dot(self.vmx.get_method(self.method))
        self.buff += "}"
        d = pydot.graph_from_dot_data(self.buff)
        if d:
            self.xdot = d.create_xdot()


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




    
