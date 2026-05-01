========================
EXECUTION DESCRIPTION
========================

To run the project from the command line, buid the jar, go to the dist folder and
type the following:

set the data in <home directory>/datos_ensembl/ 

java -jar bioinformants.jar <bp upstream> <mRNA|CDS>

for instance:

java -jar bioinformants.jar predictor.Data 1000 mRNA

java -jar bioinformants.jar predictor.Data 100 CDS
