/** A simple base64 encoding and decoding utility class
 * it can also encode and decode non ASII characters such as 
 * Chinese
 */

/**
 * This software is provided "AS IS," without a warranty of any kind.
 * anyone can use it for free,emails are welcomed concerning bugs or
 * suggestions.
 */

/**
 * Base64.java.  
 *
 * @version 1.0 06/19/2001
 * @author Wen Yu, yuwen_66@yahoo.com
 */
package com.shuto.mam.webservice.ebs.client;

public class ErpBase64
   {

    private static final char[] base64Map =  //base64 character table

         { 'A','B','C','D','E','F','G','H','I','J','K','L','M','N',
           'O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b',
           'c','d','e','f','g','h','i','j','k','l','m','n','o','p',
           'q','r','s','t','u','v','w','x','y','z','0','1','2','3',
           '4','5','6','7','8','9','+','/' } ;
 
  /** convert the platform dependent string characters to UTF8 which can 
    * also be done by calling the java String method getBytes("UTF-8"),but I
	* hope to do it from the ground up.
	*/

    private static byte[] toUTF8ByteArray(String s) 
	{
		 int ichar; 
		 byte buffer[]=new byte[3*(s.length())];
         byte hold[];
		 int index=0;
	     int count=0; //count the actual bytes in the
	                      //buffer array 		 
			 
		 for(int i=0;i<s.length();i++)
		 { 
		       ichar = (int)s.charAt(i);

		  	   //determine the bytes for a specific character
			   if((ichar>=0x0080)&(ichar<=0x07FF))
				 {
				   buffer[index++] = (byte)((6<<5)|((ichar>>6)&31));
				   buffer[index++] = (byte)((2<<6)|(ichar&63));
				   count+=2;
				 }
			  
			   //determine the bytes for a specific character
			   else if((ichar>=0x0800)&(ichar<=0x0FFFF))
				 {
				   buffer[index++] = (byte)((14<<4)|((ichar>>12)&15));
				   buffer[index++] = (byte)((2<<6)|((ichar>>6)&63));
				   buffer[index++] = (byte)((2<<6)|(ichar&63));
				   count+=3;
				 }
				 
		      //determine the bytes for a specific character
			   else if((ichar>=0x0000)&(ichar<=0x007F))
				 {
                   buffer[index++] = (byte)((0<<7)|(ichar&127));  
				   count+=1;
				 }

              //longer than 16 bit Unicode is not supported
			   else throw new RuntimeException("Unsupported encoding character length!\n");
		 }
         hold = new byte[count];
         System.arraycopy(buffer,0,hold,0,count); //trim to size
         return hold ;
	}

    public static String encode(String s)
    {
	 	 byte buf[] = toUTF8ByteArray(s);
	     return encode(buf);
    }

	public static String encode(byte buf[]) 
    {     
         StringBuffer sb = new StringBuffer();
         String padder = "" ; 
			 
         if( buf.length == 0 )  return "" ; 
       
         //cope with less than 3 bytes conditions at the end of buf
 
		 switch( buf.length%3)
         {
               case 1 : 
               { 
                  padder += base64Map[(( buf[buf.length-1] >>> 2 ) & 63) ] ;
                  padder += base64Map[ (( buf[buf.length-1] << 4 ) & 63) ] ;     
                  padder += "==" ;
                  break ;
               }      
               case 2 :
               {
                  padder += base64Map[ ( buf[buf.length-2] >>> 2 ) & 63 ] ;
                  padder += base64Map[ (((buf[buf.length-2] << 4 )&63)) | ((( buf[buf.length-1] >>>4 ) & 63)) ] ;          
                  padder += base64Map[ ( buf[buf.length-1] << 2 ) & 63 ] ;          
                  padder += "=" ;
                  break ;
               }
               default :    break ;
         }           

         int temp =0 ;
         int index = 0 ;
          
		 //encode buf.length-buf.length%3 bytes which must be a multiply of 3

         for( int i=0 ; i < ( buf.length-(buf.length % 3) ) ; )
         {
		      //get three bytes and encode them to four base64 characters
              temp = ((buf[i++] << 16)&0xFF0000)|((buf[i++] << 8)&0xFF00)|(buf[i++]&0xFF) ;
              index = (temp >> 18) & 63 ;        
              sb.append(base64Map[ index ]);
              if(sb.length()%76==0)//a Base64 encoded line is no longer than 76 characters
                  sb.append('\n');
  
              index = (temp >> 12 ) & 63 ;
              sb.append(base64Map[ index ]);
              if(sb.length()%76==0)
                  sb.append('\n');
           
		      index = (temp >> 6 ) & 63 ;
              sb.append(base64Map[ index ]);
              if(sb.length()%76==0)
                  sb.append('\n');

              index = temp & 63 ;
              sb.append(base64Map[ index ]);
              if(sb.length()%76==0)
                  sb.append('\n');
         }

         sb.append(padder);  //add the remaining one or two bytes
         return sb.toString(); 
   }

    public static String decode(String s) throws Exception
    {
     	 byte buf[] = decodeToByteArray(s) ;
	     return new String(buf,"UTF-8") ;
    }
	
	public static byte[] decodeToByteArray(String s) throws Exception
    {
         byte hold[];

	     if( s.length() == 0 )  return null ; 
         byte buf[] = s.getBytes("iso-8859-1") ;
         byte debuf[] = new byte[buf.length*3/4] ;
         byte tempBuf[]=new byte[4] ;
         int index=0;
         int index1=0;
         int temp;
	     int count=0;
         int count1=0;
		 
		 //decode to byte array
         for(int i=0; i<buf.length;i++)
         {
             if(buf[i]>=65 && buf[i]<91)
               tempBuf[index++]=(byte)(buf[i]-65);
             else if(buf[i]>=97 && buf[i]<123)
               tempBuf[index++]=(byte)(buf[i]-71);
             else if(buf[i]>=48 && buf[i]<58)
               tempBuf[index++]=(byte)(buf[i]+4);
             else if(buf[i]=='+')
               tempBuf[index++]=62;
             else if(buf[i]=='/')
               tempBuf[index++]=63;
             else if(buf[i]=='=')
			 {
               tempBuf[index++]=0;
               count1++;
			 }

             //Discard line breaks and other nonsignificant characters
			 else
             {
               if(buf[i]=='\n' || buf[i]=='\r' || buf[i]==' ' || buf[i]=='\t')
                  continue;
               else   throw new RuntimeException("Illegal character found in encoded string!");
             }
             if(index==4)
             { 
               temp=((tempBuf[0]<<18)) | (( tempBuf[1] <<12)) | ((tempBuf[2]<<6)) | (tempBuf[3]) ;
               debuf[index1++]=(byte)(temp>>16) ;
               debuf[index1++]=(byte)((temp>>8)&255) ;
               debuf[index1++]=(byte)(temp&255) ;
               count+=3;
			   index=0;
             }
         }
	     hold = new byte[count-count1];
         System.arraycopy(debuf,0,hold,0,count-count1); //trim to size
         return hold ;
    }
	public static void main(String[] args) {
		try {
			String str = decode("PERBVEFJTkZPPgo8SEVBREVSPgo8b3JnYW5pemF0aW9uX2lkPjE0MjU8L29yZ2FuaXphdGlvbl9pZD4KPHNlY29uZGFyeV9pbnZlbnRvcnlfY29kZT7otYTmnKzmgKfmlK/lh7rlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7otYTmnKzmgKfmlK/lh7rlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT4KPG1heGltb19vcmdfY29kZT5URVNUPC9tYXhpbW9fb3JnX2NvZGU+CjxtYXhpbW9fbG9jYXRpb24+VEVTVDwvbWF4aW1vX2xvY2F0aW9uPgo8ZGlzYWJsZV9kYXRlPjwvZGlzYWJsZV9kYXRlPgo8Y3JlYXRpb25fZGF0ZT4yMDE0LTA1LTA2PC9jcmVhdGlvbl9kYXRlPgo8bGFzdF91cGRhdGVfZGF0ZT4yMDE0LTA1LTA2PC9sYXN0X3VwZGF0ZV9kYXRlPgo8L0hFQURFUj4KPEhFQURFUj4KPG9yZ2FuaXphdGlvbl9pZD4xNDI1PC9vcmdhbml6YXRpb25faWQ+CjxzZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+5a+E5ZSu54mp6LWE5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+CjxzZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+5a+E5ZSu54mp6LWE5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNS0wNjwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNS0wNjwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTQyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPui0ueeUqOWtkOW6k+WtmF/kuI3ot5/ouKrmlbDph488L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7otLnnlKjlrZDlupPlrZhf5LiN6Lef6Liq5pWw6YePPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0yMDwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0yMDwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTQyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPui0ueeUqOWtkOW6k+WtmF/ot5/ouKrmlbDph488L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7otLnnlKjlrZDlupPlrZhf6Lef6Liq5pWw6YePPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0yMDwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0yMDwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTQyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPuS7k+W6kzE8L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7ku5PlupMxPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0wNTwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0yNTwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTUyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPui1hOacrOaAp+aUr+WHuuW6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPui1hOacrOaAp+aUr+WHuuW6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPgo8bWF4aW1vX29yZ19jb2RlPjwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPjwvbWF4aW1vX2xvY2F0aW9uPgo8ZGlzYWJsZV9kYXRlPjwvZGlzYWJsZV9kYXRlPgo8Y3JlYXRpb25fZGF0ZT4yMDE0LTA2LTAzPC9jcmVhdGlvbl9kYXRlPgo8bGFzdF91cGRhdGVfZGF0ZT4yMDE0LTA2LTAzPC9sYXN0X3VwZGF0ZV9kYXRlPgo8L0hFQURFUj4KPEhFQURFUj4KPG9yZ2FuaXphdGlvbl9pZD4xNTI1PC9vcmdhbml6YXRpb25faWQ+CjxzZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+6IGU5YKo54mp6LWE5YCf5Ye66Jma5ouf5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+CjxzZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+6IGU5YKo54mp6LWE5YCf5Ye66Jma5ouf5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+PC9tYXhpbW9fb3JnX2NvZGU+CjxtYXhpbW9fbG9jYXRpb24+PC9tYXhpbW9fbG9jYXRpb24+CjxkaXNhYmxlX2RhdGU+PC9kaXNhYmxlX2RhdGU+CjxjcmVhdGlvbl9kYXRlPjIwMTQtMDYtMDM8L2NyZWF0aW9uX2RhdGU+CjxsYXN0X3VwZGF0ZV9kYXRlPjIwMTQtMDYtMDM8L2xhc3RfdXBkYXRlX2RhdGU+CjwvSEVBREVSPgo8SEVBREVSPgo8b3JnYW5pemF0aW9uX2lkPjE1MjU8L29yZ2FuaXphdGlvbl9pZD4KPHNlY29uZGFyeV9pbnZlbnRvcnlfY29kZT7lr4TllK7nianotYTlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7lr4TllK7nianotYTlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT4KPG1heGltb19vcmdfY29kZT48L21heGltb19vcmdfY29kZT4KPG1heGltb19sb2NhdGlvbj48L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0wMzwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0wMzwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTUyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPuiBlOWCqOeJqei1hOW6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPuiBlOWCqOeJqei1hOW6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPgo8bWF4aW1vX29yZ19jb2RlPjwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPjwvbWF4aW1vX2xvY2F0aW9uPgo8ZGlzYWJsZV9kYXRlPjwvZGlzYWJsZV9kYXRlPgo8Y3JlYXRpb25fZGF0ZT4yMDE0LTA2LTAzPC9jcmVhdGlvbl9kYXRlPgo8bGFzdF91cGRhdGVfZGF0ZT4yMDE0LTA2LTAzPC9sYXN0X3VwZGF0ZV9kYXRlPgo8L0hFQURFUj4KPEhFQURFUj4KPG9yZ2FuaXphdGlvbl9pZD4xNDI1PC9vcmdhbml6YXRpb25faWQ+CjxzZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+6IGU5YKo54mp6LWE5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+CjxzZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+6IGU5YKo54mp6LWE5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNS0wNjwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNS0wNjwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTQyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPuiBlOWCqOeJqei1hOWAn+WHuuiZmuaLn+W6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPuiBlOWCqOeJqei1hOWAn+WHuuiZmuaLn+W6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPgo8bWF4aW1vX29yZ19jb2RlPlRFU1Q8L21heGltb19vcmdfY29kZT4KPG1heGltb19sb2NhdGlvbj5URVNUPC9tYXhpbW9fbG9jYXRpb24+CjxkaXNhYmxlX2RhdGU+PC9kaXNhYmxlX2RhdGU+CjxjcmVhdGlvbl9kYXRlPjIwMTQtMDUtMDY8L2NyZWF0aW9uX2RhdGU+CjxsYXN0X3VwZGF0ZV9kYXRlPjIwMTQtMDUtMDY8L2xhc3RfdXBkYXRlX2RhdGU+CjwvSEVBREVSPgo8SEVBREVSPgo8b3JnYW5pemF0aW9uX2lkPjE1MjU8L29yZ2FuaXphdGlvbl9pZD4KPHNlY29uZGFyeV9pbnZlbnRvcnlfY29kZT7miqXlup/lk4HlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7miqXlup/lk4HlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT4KPG1heGltb19vcmdfY29kZT48L21heGltb19vcmdfY29kZT4KPG1heGltb19sb2NhdGlvbj48L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0wMzwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0wMzwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTUyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPui0ueeUqOWtkOW6k+WtmDwvc2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPui0ueeUqOWtkOW6k+WtmDwvc2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPgo8bWF4aW1vX29yZ19jb2RlPjwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPjwvbWF4aW1vX2xvY2F0aW9uPgo8ZGlzYWJsZV9kYXRlPjwvZGlzYWJsZV9kYXRlPgo8Y3JlYXRpb25fZGF0ZT4yMDE0LTA2LTAzPC9jcmVhdGlvbl9kYXRlPgo8bGFzdF91cGRhdGVfZGF0ZT4yMDE0LTA2LTAzPC9sYXN0X3VwZGF0ZV9kYXRlPgo8L0hFQURFUj4KPEhFQURFUj4KPG9yZ2FuaXphdGlvbl9pZD4xNDI1PC9vcmdhbml6YXRpb25faWQ+CjxzZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+5oql5bqf5ZOB5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+CjxzZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+5oql5bqf5ZOB5bqTPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNS0wNjwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNS0wNjwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTQyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPui0ueeUqOWtkOW6k+WtmDwvc2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPui0ueeUqOWtkOW6k+WtmDwvc2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPgo8bWF4aW1vX29yZ19jb2RlPlRFU1Q8L21heGltb19vcmdfY29kZT4KPG1heGltb19sb2NhdGlvbj5URVNUPC9tYXhpbW9fbG9jYXRpb24+CjxkaXNhYmxlX2RhdGU+PC9kaXNhYmxlX2RhdGU+CjxjcmVhdGlvbl9kYXRlPjIwMTQtMDUtMDY8L2NyZWF0aW9uX2RhdGU+CjxsYXN0X3VwZGF0ZV9kYXRlPjIwMTQtMDUtMDY8L2xhc3RfdXBkYXRlX2RhdGU+CjwvSEVBREVSPgo8SEVBREVSPgo8b3JnYW5pemF0aW9uX2lkPjE1MjU8L29yZ2FuaXphdGlvbl9pZD4KPHNlY29uZGFyeV9pbnZlbnRvcnlfY29kZT7nu4/okKXmgKfmlK/lh7rlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7nu4/okKXmgKfmlK/lh7rlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT4KPG1heGltb19vcmdfY29kZT48L21heGltb19vcmdfY29kZT4KPG1heGltb19sb2NhdGlvbj48L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0wMzwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0wMzwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjxIRUFERVI+Cjxvcmdhbml6YXRpb25faWQ+MTQyNTwvb3JnYW5pemF0aW9uX2lkPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPui1hOacrOaAp+aUr+WHuuW6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9jb2RlPgo8c2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPui1hOacrOaAp+aUr+WHuuW6kzwvc2Vjb25kYXJ5X2ludmVudG9yeV9uYW1lPgo8bWF4aW1vX29yZ19jb2RlPlRFU1Q8L21heGltb19vcmdfY29kZT4KPG1heGltb19sb2NhdGlvbj5URVNUPC9tYXhpbW9fbG9jYXRpb24+CjxkaXNhYmxlX2RhdGU+PC9kaXNhYmxlX2RhdGU+CjxjcmVhdGlvbl9kYXRlPjIwMTQtMDYtMTc8L2NyZWF0aW9uX2RhdGU+CjxsYXN0X3VwZGF0ZV9kYXRlPjIwMTQtMDYtMTc8L2xhc3RfdXBkYXRlX2RhdGU+CjwvSEVBREVSPgo8SEVBREVSPgo8b3JnYW5pemF0aW9uX2lkPjE0MjU8L29yZ2FuaXphdGlvbl9pZD4KPHNlY29uZGFyeV9pbnZlbnRvcnlfY29kZT7nu4/okKXmgKfmlK/lh7rlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfY29kZT4KPHNlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT7nu4/okKXmgKfmlK/lh7rlupM8L3NlY29uZGFyeV9pbnZlbnRvcnlfbmFtZT4KPG1heGltb19vcmdfY29kZT5URVNUPC9tYXhpbW9fb3JnX2NvZGU+CjxtYXhpbW9fbG9jYXRpb24+VEVTVDwvbWF4aW1vX2xvY2F0aW9uPgo8ZGlzYWJsZV9kYXRlPjwvZGlzYWJsZV9kYXRlPgo8Y3JlYXRpb25fZGF0ZT4yMDE0LTA1LTA2PC9jcmVhdGlvbl9kYXRlPgo8bGFzdF91cGRhdGVfZGF0ZT4yMDE0LTA1LTA2PC9sYXN0X3VwZGF0ZV9kYXRlPgo8L0hFQURFUj4KPEhFQURFUj4KPG9yZ2FuaXphdGlvbl9pZD4xNDI1PC9vcmdhbml6YXRpb25faWQ+CjxzZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+6LWE5Lqn5a2Q5bqT5a2YPC9zZWNvbmRhcnlfaW52ZW50b3J5X2NvZGU+CjxzZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+6LWE5Lqn5a2Q5bqT5a2YPC9zZWNvbmRhcnlfaW52ZW50b3J5X25hbWU+CjxtYXhpbW9fb3JnX2NvZGU+VEVTVDwvbWF4aW1vX29yZ19jb2RlPgo8bWF4aW1vX2xvY2F0aW9uPlRFU1Q8L21heGltb19sb2NhdGlvbj4KPGRpc2FibGVfZGF0ZT48L2Rpc2FibGVfZGF0ZT4KPGNyZWF0aW9uX2RhdGU+MjAxNC0wNi0yMDwvY3JlYXRpb25fZGF0ZT4KPGxhc3RfdXBkYXRlX2RhdGU+MjAxNC0wNi0yMDwvbGFzdF91cGRhdGVfZGF0ZT4KPC9IRUFERVI+CjwvREFUQUlORk8+Cg==");
			System.out.println(str);
			System.out.println(encode("1111111"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}