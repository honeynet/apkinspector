# LaTeX2HTML 2002-2-1 (1.71)
# Associate labels original text with physical files.


$key = q/FIG:LATTICE/;
$external_labels{$key} = "$URL/" . q|sideeffect.html|; 
$noresave{$key} = "$nosave";

$key = q/FIG:SEFORMAT/;
$external_labels{$key} = "$URL/" . q|sideeffect.html|; 
$noresave{$key} = "$nosave";

$key = q/FIG:SEEXAMPLE/;
$external_labels{$key} = "$URL/" . q|sideeffect.html|; 
$noresave{$key} = "$nosave";

1;


# LaTeX2HTML 2002-2-1 (1.71)
# labels from external_latex_labels array.


$key = q/FIG:LATTICE/;
$external_latex_labels{$key} = q|1|; 
$noresave{$key} = "$nosave";

$key = q/FIG:SEFORMAT/;
$external_latex_labels{$key} = q|2|; 
$noresave{$key} = "$nosave";

$key = q/FIG:SEEXAMPLE/;
$external_latex_labels{$key} = q|3|; 
$noresave{$key} = "$nosave";

1;

