#!/bin/sh

#Check Ubuntu version
read line < /etc/issue
str1="Ubuntu 12.04 LTS n l"
str2="Ubuntu 11.10 n l"
if [ "$str1" = "$line" ]; then
   echo "Your Ubuntu Version is 12.04 LTS n l, supported by APKInspector."
elif [ "$str2" = "$line" ]; then
   echo "Your Ubuntu Version is 11.10, supported by APKInspector."
else
    echo "Your Ubuntu Version is $line, please install 11.10/12.04 in order to run APKinspector"
    exit 0
fi

#######Install QTSDK
echo "please input your password"
read yourpassword
echo "yourpassword"|sudo -S apt-get install build-essential 
#echo "y"|sudo -S apt-get install subversion
chmod u+x  Qt_SDK_Lin32_offline_v1_2_en.run
sudo ./Qt_SDK_Lin32_offline_v1_2_en.run
sudo chmod 777 /etc/profile
cat >> /etc/profile << EFF
############################
QTDIR=/opt/QtSDK/Desktop/Qt/473/gcc
PATH=$QTDIR/bin:$PATH
LD_LIBRARY_PATH=$QTDIR/lib:$LD_LIBRARY_PATH
export QTDIR PATH LD_LIBRARY_PATH
#############################
EFF

#echo "$yourpassword"|sudo -S  updatedb
echo "y"|sudo -S apt-get install qt4-qmake
echo "y"|sudo -S apt-get install libqt4-dev

qmake -v
if [ "$?" != 0 ]; then
    echo "QT is not successfuly installed."
    exit 0
else 
    echo "QT is successfuly installed."
fi
   

##Install Python-dev
echo "y"|sudo -S apt-get install python-dev
echo "sssssssssssssssssssssssssssssssssssssss"
######Install SIP  ###latest version: 4.13.3
tar zxvf sip-4.13.3.tar.gz
cd sip-4.13.3
echo "y"|sudo -S python configure.py
echo "y"|sudo -S make
echo "y"|sudo -S make install
echo "####################################"
cd ..

######Install pyqt4  ###latest version:4.9.4
tar zxvf PyQt-x11-gpl-4.9.4.tar.gz
cd PyQt-x11-gpl-4.9.4
echo "yes"|sudo -S python configure.py -g
echo "$yourpassword"|sudo -S make
echo "111111111111111111111111111111111111"
echo "$yourpassword"|sudo -S make install
cd ..

#######Install pydot  ###latest version:1.0.28
tar zxvf pydot-1.0.28.tar.gz
cd pydot-1.0.28
echo "y"|sudo python setup.py install
cd ..


#######Install Graphviz
tar zxvf graphviz-2.28.0.tar.gz
cd graphviz-2.28.0
echo "$yourpassword"|sudo -S ./configure --with-ortho=yes
echo "$yourpassword"|sudo -S make
echo "$yourpassword"|sudo -S make install
cd ..


#####Install apktool

tar jxvf apktool1.4.3.tar.bz2  
tar jxvf apktool-install-linux-r04-brut1.tar.bz2  
echo "$yourpassword"|sudo -S cp apktool* /usr/local/bin

######Lib-dependency
echo "$yourpassword"|sudo -S apt-get install ipython
echo "y"|sudo -S apt-get install python-scipy
echo "y"|sudo -S apt-get install gtk2-engines-pixbuf


######Install JDK
echo "y"|sudo -S apt-get install openjdk-7-jdk

######Install pyparsing
echo "$yourpassword"|sudo -S apt-get install python-setuptools
echo "y"|sudo -S easy_install pyparsing

######Run Apkinspector
#unzip apkinspector.Beta.zip
#cd apkinspector
python startQT.py
