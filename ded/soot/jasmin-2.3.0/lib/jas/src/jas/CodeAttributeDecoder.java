// CHANGES
// This is a customized copy of soot.tagkits.JasminAttribute class.
// It decodes the CodeAttribute format.
// Read comments on CodeAttr.java for more information.

// Feng Qian
// Jan 25, 2001

/* Soot - a J*va Optimization Framework
 * Copyright (C) 2000 Patrice Pominville and Feng Qian
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package jas;

import java.util.*;
import java.io.*;


/**
 *  This class  must be extended  by Attributes that can 
 *  be emitted in Jasmin. The attributes must format their data
 *  in Base64 and if Unit references they may contain must be emitted as
 *  labels embedded and
 *  escaped in the attribute's Base64 data stream at the location where the value
 *  of their pc is to occur. For example:
<pre> 
    aload_1
    iload_2
    label2:
    iaload
 label3:
    iastore
    iinc 2 1
    label0:
    iload_2
    aload_0
    arraylength
 label4:
   if_icmplt label1
   return
 .code_attribute ArrayCheckAttribute "%label2%Aw==%label3%Ag==%label4%Ag=="

</pre>
 *
 */


class CodeAttributeDecoder
{
    public static byte[] decode(String attr, Hashtable labelToPc)
    {
	List attributeHunks = new LinkedList();
	int attributeSize = 0;
	int tableSize = 0;
	
	StringTokenizer st = new StringTokenizer(attr, "%", true);
	boolean isLabel = false;

	byte[] pcArray;
	while(st.hasMoreTokens()) {	    
	    String token = st.nextToken();
	    if( token.equals( "%" ) ) {
		isLabel = !isLabel;
		continue;
	    }
	    if(isLabel) {		
		Integer pc = (Integer) labelToPc.get(token);

		if(pc == null)
		    throw new RuntimeException("PC is null, the token is "+token);

		int pcvalue = pc.intValue();
		if(pcvalue > 65535) 
		    throw new RuntimeException("PC great than 65535, the token is "+token+" : " +pcvalue);

		pcArray = new byte[2];

		pcArray[1] = (byte)(pcvalue&0x0FF);
				
		pcArray[0] = (byte)((pcvalue>>8)&0x0FF);

		attributeHunks.add(pcArray);
		attributeSize += 2;

		tableSize++;

	    } else {

		byte[] hunk = Base64.decode(token.toCharArray());		
		attributeSize += hunk.length;

		attributeHunks.add(hunk);
	    }
	}

	attributeSize += 2;
	byte[] attributeValue = new byte[attributeSize];
	attributeValue[0] = (byte)((tableSize>>8)&0x0FF);
	attributeValue[1] = (byte)(tableSize&0x0FF);

	int index = 2;

	Iterator it = attributeHunks.iterator();
	while(it.hasNext()) {
	    byte[] hunk = (byte[]) it.next();
	    for(int i = 0; i < hunk.length; i++) {
		attributeValue[index++] = hunk[i];
	    }
	}

	if(index != (attributeSize))
	    throw new RuntimeException("Index does not euqal to attrubute size :"+index+" -- "+attributeSize);

	return attributeValue;
    }
}
