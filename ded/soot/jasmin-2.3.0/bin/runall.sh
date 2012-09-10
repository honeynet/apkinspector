#!/bin/csh

#
# Compiles and then runs all of the classes in the examples
# directory, then tidies up afterwards.
#

#
# Compile
#
echo "Compiling examples:"
bin/jasmin -g examples/*.j

#
# Build CLASSES list
#
cd examples
cat /dev/null >! CLASSES
foreach i (*.class) 
    echo $i:r >>! CLASSES
end
cd ..

#
# Run the examples
#
echo "Running examples:"

foreach i (`cat examples/CLASSES`)
    echo $i\:
    java -verify examples.$i foo bar
    echo ""
end

#
# Now remove the class files and the CLASSES list
#
cd examples
/bin/rm -f *.class CLASSES

