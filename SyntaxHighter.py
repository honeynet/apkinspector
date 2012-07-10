from PyQt4.QtGui import *
from PyQt4.QtCore import *

class Highlighter(QSyntaxHighlighter):
    keywordFormat1 = QTextCharFormat()
    keywordFormat2 = QTextCharFormat()
    numberFormat = QTextCharFormat()
    

    
    
    def __init__(self, parent):
        QSyntaxHighlighter.__init__(self, parent)
        self.highlightingRules = []
        
        self.keywordFormat1.setForeground(QColor(0, 102, 204))
        self.keywordFormat1.setFontWeight(QFont.Bold)
        self.keywordFormat2.setForeground(QColor(51, 153, 51))
        self.keywordFormat2.setFontWeight(QFont.Bold)


        # set the number format
        self.numberFormat.setForeground(QColor(102, 51, 10))
        self.numberFormat.setFontWeight(QFont.Bold)
        
        # define the method format
        methodFormatLabel = QTextCharFormat()
        methodFormatClass = QTextCharFormat()
        methodFormatParameter = QTextCharFormat()
        methodFormatReturn = QTextCharFormat()
        methodFormatName = QTextCharFormat()
        
        # set the method format
        methodFormatLabel.setForeground(QColor(255, 0, 51))
        methodFormatLabel.setFontWeight(QFont.Bold)
        methodFormatClass.setForeground(QColor(255, 204, 0))
        methodFormatClass.setFontWeight(QFont.Bold)
        methodFormatParameter.setForeground(QColor(153, 51, 204))
        methodFormatParameter.setFontWeight(QFont.Bold)
        methodFormatReturn.setForeground(QColor(0, 102, 51))
        methodFormatReturn.setFontWeight(QFont.Bold)
        methodFormatName.setForeground(QColor(102, 51, 102))
        methodFormatName.setFontWeight(QFont.Bold)
        self.methodFormat = [methodFormatLabel, methodFormatClass, methodFormatParameter, methodFormatReturn, methodFormatName]

        # define the method pattern
        self.methodPattern = QRegExp("\\[meth@.*\\]")
        
        # define the string format
        stringFormatLabel = QTextCharFormat()
        stringFormatValue = QTextCharFormat()
        
        # set the string format
        stringFormatLabel.setForeground(QColor(255, 0, 51))
        stringFormatLabel.setFontWeight(QFont.Bold)
        stringFormatValue.setForeground(QColor(153, 204, 255))
        stringFormatValue.setFontWeight(QFont.Bold)
        self.stringFormat = [stringFormatLabel, stringFormatValue]

        # deine the string pattern
        self.stringPattern = QRegExp("\\[string@.*\\]")

        # define the type format
        typeFormatLabel = QTextCharFormat()
        typeFormatValue = QTextCharFormat()
        
        # set the type format
        typeFormatLabel.setForeground(QColor(255, 0, 51))
        typeFormatLabel.setFontWeight(QFont.Bold)
        typeFormatValue.setForeground(QColor(153, 102, 0))
        typeFormatValue.setFontWeight(QFont.Bold)
        self.typeFormat = [typeFormatLabel, typeFormatValue]
        
        # deine the type pattern
        self.typePattern = QRegExp("\\[type@.*\\]")    
    
        # define the field format
        fieldFormatLabel = QTextCharFormat()
        fieldFormatClass = QTextCharFormat()
        fieldFormatType = QTextCharFormat()
        fieldFormatName = QTextCharFormat()
        
        # set the field format
        fieldFormatLabel.setForeground(QColor(255, 0, 51))
        fieldFormatLabel.setFontWeight(QFont.Bold)
        fieldFormatClass.setForeground(QColor(255, 204, 0))
        fieldFormatClass.setFontWeight(QFont.Bold)
        fieldFormatType.setForeground(QColor(77, 100, 23))
        fieldFormatType.setFontWeight(QFont.Bold)
        fieldFormatName.setForeground(QColor(102, 51, 102))
        fieldFormatName.setFontWeight(QFont.Bold)
        self.fieldFormat = [fieldFormatLabel, fieldFormatClass, fieldFormatType, fieldFormatName]
        
        # deine the field pattern
        self.fieldPattern = QRegExp("\\[field@.*\\]")   
        
        # define the number pattern
        numberPattern = QRegExp("\\b[0-9]+\\b")
        self.highlightingRules.append([numberPattern, self.numberFormat])

        
        # define the keyword pattern
        keywordPatterns1 = [ "add-double ", "add-double/2addr ", "add-float ", "add-float/2addr ", "add-int ", "add-int/2addr ",
                             "add-int/lit16 ", "add-int/lit8 ", "add-long ", "add-long/2addr ", "aget ", "aget-boolean ", 
                             "aget-byte ", "aget-char ", "aget-object ", "aget-short ", "aget-wide ", "and-int ", "and-int/2addr ", 
                             "and-int/lit16 ", "and-int/lit8 ", "and-long ", "and-long/2addr ", "aput ", "aput-boolean ", "aput-byte ", 
                             "aput-char ", "aput-object ", "aput-short ", "aput-wide ", "array-length ", "check-cast ", "cmp-long ", 
                             "cmpg-double ", "cmpg-float ", "cmpl-double ", "cmpl-float ", "const ", "const-class ", "const-string ", 
                             "const-string-jumbo ", "const-wide ", "const-wide/16 ", "const-wide/32 ", "const-wide/high16 ", "const/16 ", 
                             "const/4 ", "const/high16 ", "div-double ", "div-double/2addr ", "div-float ", "div-float/2addr ", "div-int ", 
                             "div-int/2addr ", "div-int/lit16 ", "div-int/lit8 ", "div-long ", "div-long/2addr ", "double-to-float ", 
                             "double-to-int ", "double-to-long ", "execute-inline ", "fill-array-data ", "filled-new-array ", 
                             "filled-new-array/range ", "float-to-double ", "float-to-int ", "float-to-long ", "goto ", "goto/16 ", 
                             "goto/32 ", "if-eq ", "if-eqz ", "if-ge ", "if-gez ", "if-gt ", "if-gtz ", "if-le ", "if-lez ", "if-lt ", "if-ltz ", 
                             "if-ne ", "if-nez ", "iget ", "iget-boolean ", "iget-byte ", "iget-char ", "iget-object ", "iget-object-quick ", 
                             "iget-quick ", "iget-short ", "iget-wide ", "iget-wide-quick ", "instance-of ", "int-to-byte ", "int-to-char ", 
                             "int-to-double ", "int-to-float ", "int-to-long ", "int-to-short ", "invoke-direct ", "invoke-direct-empty ", 
                             "invoke-direct/range ", "invoke-interface ", "invoke-interface/range ", "invoke-static ", "invoke-static/range ",
                             "invoke-super ", "invoke-super-quick ", "invoke-super-quick/range ", "invoke-super/range ", "invoke-virtual ", 
                             "invoke-virtual-quick ", "invoke-virtual-quick/range ", "invoke-virtual/range ", "iput ", "iput-boolean ", 
                             "iput-byte ", "iput-char ", "iput-object ", "iput-object-quick ", "iput-quick ", "iput-short ", "iput-wide ", 
                             "iput-wide-quick ", "long-to-double ", "long-to-float ", "long-to-int ", "monitor-enter ", "monitor-exit ", 
                             "move ", "move-exception ", "move-object ", "move-object/16 ", "move-object/from16 ", "move-result ", 
                             "move-result-object ", "move-result-wide ", "move-wide ", "move-wide/16 ", "move-wide/from16 ", "move/16 ", 
                             "move/from16 ", "mul-double ", "mul-double/2addr ", "mul-float ", "mul-float/2addr ", "mul-int ", "mul-int/2addr ", 
                             "mul-int/lit8 ", "mul-int/lit16 ", "mul-long ", "mul-long/2addr ", "neg-double ", "neg-float ", "neg-int ", "neg-long ", 
                             "new-array ", "new-instance ", "nop ", "not-int ", "not-long ", "or-int ", "or-int/2addr ", "or-int/lit16 ", "or-int/lit8 ", 
                             "or-long ", "or-long/2addr ", "rem-double ", "rem-double/2addr ", "rem-float ", "rem-float/2addr ", "rem-int ", "rem-int/2addr ", 
                             "rem-int/lit16 ", "rem-int/lit8 ", "rem-long ", "rem-long/2addr ", "return ", "return-object ", "return-void ", "return-wide ", 
                             "sget ", "sget-boolean ", "sget-byte ", "sget-char ", "sget-object ", "sget-short ", "sget-wide ", "shl-int ", "shl-int/2addr ", 
                             "shl-int/lit8 ", "shl-long ", "shl-long/2addr ", "shr-int ", "shr-int/2addr ", "shr-int/lit8 ", "shr-long ", "shr-long/2addr ", 
                             "sparse-switch ", "sput ", "sput-boolean ", "sput-byte ", "sput-char ", "sput-object ", "sput-short ", "sput-wide ", 
                             "sub-double ", "sub-double/2addr ", "sub-float ", "sub-float/2addr ", "sub-int ", "sub-int/2addr ", "sub-int/lit16 ", 
                             "sub-int/lit8 ", "sub-long ", "sub-long/2addr ", "throw ", "ushr-int ", "ushr-int/2addr ", "ushr-int/lit8 ", "ushr-long ", 
                             "ushr-long/2addr ", "xor-int ", "xor-int/2addr ", "xor-int/lit16 ", "xor-int/lit8 ", "xor-long ", "xor-long/2addr "]
        
        keywordPatterns2 = ["v0", "v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11", "v12", "v13", "v14", "v15", 
                            "v16", "v17", "v18", "v19", "v20", "v21", "v22", "v23", "v24", "v25", "v26", "v27", "v28", "v29", "v30", 
                            "v31", "v32", "v33", "v34", "v35", "v36", "v37", "v38", "v39", "v40", "p0", "p1", "p2", "p3", "p4", "p5", 
                            "p6", "p7", "p8", "p9", "p10", "p11", "p12", "p13", "p14", "p15", "p16", "p17", "p18", "p19", "p20"]
        
        keywordPatterns = keywordPatterns1 + keywordPatterns2 
        for pattern in keywordPatterns1:
            self.highlightingRules.append([QRegExp(pattern), self.keywordFormat1])
        for pattern in keywordPatterns2:
            self.highlightingRules.append([QRegExp(pattern), self.keywordFormat2])            

            
    def highlightBlock(self, text): 
        # highlight the keywords , numbers .etc in the highlightingRules
        for rule in self.highlightingRules:
            expression = QRegExp(rule[0])
            index = expression.indexIn(text)
            while index >= 0:
                length = expression.matchedLength()
                self.setFormat(index, length, rule[1])
                index = expression.indexIn(text, index + length)
    
        
        # highlight the method
        expression = QRegExp(self.methodPattern)
        index = expression.indexIn(text)
        while index >= 0:
            length = expression.matchedLength()
            method = str(text[index:index+length])
            method2 = method.split(" ")
            
            # set the label format
            LabelStartIndex = index + 1
            LabelEndIndex = index + len(method2[0]) + len(method2[1]) + 1 
            self.setFormat(LabelStartIndex , LabelEndIndex - LabelStartIndex, self.methodFormat[0])
            
            # set the class format
            ClassStartIndex = LabelEndIndex + 1
            self.setFormat(ClassStartIndex , len(method2[2]), self.methodFormat[1])
            
            # set the return format
            ReturnStartIndex = index + method.rindex(")") + 2
            self.setFormat(ReturnStartIndex, len(method2[-2]), self.methodFormat[3])
            
            # set the method name format
            NameStartIndex = index + method.rindex(" ") + 1
            self.setFormat(NameStartIndex, len(method2[-1]) - 1, self.methodFormat[4])
            
            # set the parameter format
            ParameterStartIndex = index + method.index("(")
            PareameterEndIndex = index + method.rindex(")") + 1
            self.setFormat(ParameterStartIndex, PareameterEndIndex - ParameterStartIndex, self.methodFormat[2])
            
            index = expression.indexIn(text, index + length)
            
            
        # highlight the string
        expression = QRegExp(self.stringPattern)
        index = expression.indexIn(text)
        while index >= 0:
            length = expression.matchedLength()
            string = str(text[index:index+length])
            string2 = string.split(" ")
            
            # set the label format
            LabelStartIndex = index + 1
            LabelEndIndex = index + len(string2[0]) + len(string2[1]) + 1
            self.setFormat(LabelStartIndex, LabelEndIndex - LabelStartIndex, self.stringFormat[0])
            
            # set the string value format
            ValueStartIndex = LabelEndIndex + 1
            VauleEndIndex = index + string.rindex("]")
            self.setFormat(ValueStartIndex, VauleEndIndex - ValueStartIndex, self.stringFormat[1])
            
            index = expression.indexIn(text, index + length)
            
            
        # highlight the type
        expression = QRegExp(self.typePattern)
        index = expression.indexIn(text)
        while index >= 0:
            length = expression.matchedLength()
            type = str(text[index:index+length])
            type2 = type.split(" ")
            
            # set the label format
            LabelStartIndex = index + 1
            LabelEndIndex = index + len(type2[0]) + len(type2[1]) + 1
            self.setFormat(LabelStartIndex, LabelEndIndex - LabelStartIndex, self.typeFormat[0])
            
            # set the type value format
            ValueStartIndex = LabelEndIndex + 1
            VauleEndIndex = index + type.rindex("]")
            self.setFormat(ValueStartIndex, VauleEndIndex - ValueStartIndex, self.typeFormat[1])
        
            index = expression.indexIn(text, index + length)            
 
 
        # highlight the field
        expression = QRegExp(self.fieldPattern)
        index = expression.indexIn(text)        
        while index >= 0:
            length = expression.matchedLength()
            field = str(text[index:index+length])
            field2 = field.split(" ")
            
            # set the label format
            LabelStartIndex = index + 1
            LabelEndIndex = index + len(field2[0]) + len(field2[1]) + 1
            self.setFormat(LabelStartIndex, LabelEndIndex - LabelStartIndex, self.fieldFormat[0])
            
            # set the field class format
            ValueStartIndex = LabelEndIndex + 1
            ValueEndIndex = ValueStartIndex + len(field2[2])
            self.setFormat(ValueStartIndex, ValueEndIndex - ValueStartIndex, self.fieldFormat[1])
            
            # set the field type format
            TypeStartIndex = ValueEndIndex + 1
            TypeEndIndex = TypeStartIndex + len(field2[3])
            self.setFormat(TypeStartIndex, TypeEndIndex - TypeStartIndex, self.fieldFormat[2])
            
            # set the field name format
            NameStartIndex = TypeEndIndex + 1
            NameEndIndex = NameStartIndex + len(field2[4]) - 1
            self.setFormat(NameStartIndex, NameEndIndex - NameStartIndex, self.fieldFormat[3])
            
            index = expression.indexIn(text, index + length)            
