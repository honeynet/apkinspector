# LaTeX2HTML 2002-2-1 (1.71)
# Associate images original text with physical files.


$key = q/ensuremath{<};MSF=1.6;AAT/;
$cached_env_img{$key} = q|<IMG
 WIDTH="16" HEIGHT="30" ALIGN="MIDDLE" BORDER="0"
 SRC="|."$dir".q|img1.png"
 ALT="\ensuremath{&lt;}">|; 

$key = q/ensuremath{>};MSF=1.6;AAT/;
$cached_env_img{$key} = q|<IMG
 WIDTH="16" HEIGHT="30" ALIGN="MIDDLE" BORDER="0"
 SRC="|."$dir".q|img2.png"
 ALT="\ensuremath{&gt;}">|; 

1;

