========================
EXECUTION DESCRIPTION
========================

To run the project do the following:

1. Set an enviroment variable named WORKSPACE in this way:

set WORKSPACE=HOME-DIRECTORY/datos_ensembl/

2. Copy the datos_ensembl to your home directory

3. Execute the command java -jar bioinformants.jar <bp upstream> <mRNA|CDS>

for instance:

java -jar bioinformants.jar predictor.Data 1000 mRNA

java -jar bioinformants.jar predictor.Data 100 CDS
