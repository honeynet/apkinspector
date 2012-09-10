#!/bin/sh
#
#Copyright (C) 2011 The Pennsylvania State University
#Systems and Internet Infrastructure Security Laboratory
#
#Author:
#    Damien Octeau <octeau@cse.psu.edu>
#
#This program is free software; you can redistribute it and/or
#modify it under the terms of the GNU General Public License
#as published by the Free Software Foundation; either version 2
#of the License, or (at your option) any later version.
#
#This program is distributed in the hope that it will be useful,
#but WITHOUT ANY WARRANTY; without even the implied warranty of
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#GNU General Public License for more details.
#
#You should have received a copy of the GNU General Public License
#along with this program; if not, write to the Free Software
#Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
while getopts d:oc p
do
	case "$p" in
	[?])	echo "Usage: $0 <dex-file> [-d <output directory] [-o] [-c]"
		echo "  -o : optimize .class files with Soot"
		echo "  -c : optimize and decompile .class files with Soot"
		exit 1;;
	esac
done

SCRIPT="$0"
OLD_DIR=`pwd`
DED_DIR=`dirname "$SCRIPT"`
DED="./ded-launcher-0.7.1"
SOOT_DIR="soot"
ANDROID_LIBS="$DED_DIR/android-libs"

SOOT=$SOOT_DIR/soot-2.3.0/classes
JASMIN=$SOOT_DIR/jasmin-2.3.0/classes
POLYGLOT=$SOOT_DIR/polyglot-1.3.5/classes:$SOOT_DIR/polyglot-1.3.5/cup-classes
cd $ANDROID_LIBS
LIBS="`find *.jar -exec echo $OLD_DIR/$ANDROID_LIBS/{} \; | tr '\n' ':'`$CLASSPATH"
cd ..

$DED -s $SOOT:$JASMIN:$POLYGLOT -a $LIBS "$@"

cd $OLD_DIR

