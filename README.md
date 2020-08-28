# Eczema-Food Allergy Diagnostic Project

## Project Overview

The primary objective of this application is to provide some clarity on what it means to receive a 
positive result for a given medical diagnostic test. Specifically, it will determine the accuracy of 
the implication that a positive test result has on actually having the medical condition of concern. 
While the concept of this application could be applied to any diagnostic test (or any scenario 
involving conditional probability), this application will emphasize some of the more under-appreciated 
phenomena in society. For this reason, we will assess the apparent link between 
the medical condition known as eczema and the development of food allergies in infants.

**Terminology**

* Eczema is an umbrella term characterizing a range of skin conditions that involve inflammation, 
itchiness, cracking, and redness of the skin, and is becoming increasingly prevalent in infants in developed 
societies. 
* Another important technicality is to discern the immunological reaction (IgE-mediated 
hypersensitivity) seen in food allergies from food intolerance, which does not involve the 
immune system. Symptoms of food allergies can manifest itself in a variety of ways (e.g. anaphylaxis, hives,
 vomiting, itchiness, etc.) and be of varying degrees of severity. 

The motive for this topic lies in the disproportionate increase in food allergies in the past 
generation; furthermore, this increase is largely concentrated in Western societies. While there 
remains to be established a causal relationship between eczema and food allergies, an interesting
hypothesis suggests that exposure to certain food particles (antigens) through areas of the skin 
affected by eczema - as opposed to oral ingestion - may drive the propensity of developing food allergies 
against those foods. 

Thus, this application will focus on the conditional probability that, given the presence of eczema 
(or lack thereof), what is the likelihood of developing food allergies (or its negation). An 
application of this nature can be useful in many different ways by providing statistical insight to 
new parents, health practitioners, researchers, etc. Additionally, given a robust data collection, 
one can subset the data in a way that caters more specifically to their situation (i.e. can see how 
different variables like sex, geographical location, or ethnicity affect that outcome). Assuming its 
current trend continues, and the number of individuals afflicted with food allergies continues to
rise in many areas of the world, this application will retain its utility and relevancy for the 
foreseeable future. 

## Assumptions

* The different manifestations of food allergies will be aggregated into a Boolean value (true indicating the presence 
of food allergies and false indicating the absence of food allergies).
* The presence of eczema is also categorized under a single, binary category. This means that the severity of eczema 
will not be accounted for, and the condition will be represented as either being present or absent.
* For the purpose of this application, the two medical conditions are to be viewed under the working assumption 
that the development of eczema precedes the development of food allergies. However, in reality, this 'order' 
of development isn't necessarily true.
* If a patient is diagnosed with a food allergy, then the patient records will permanently indicate that they have a 
food allergy, and will not revert back even if they grow out of their food allergy later in life.
* To reiterate, these analyses to not indicate causality! Rather, they are measures of statistical association. 
As such, in their present forms, the findings may not translate to experimental data.
* It's important to recognize the bias for which the sample data is based on. The summary statistics from which the
 sample data are based on favours patients that are more likely to be cognizant of their predisposition to such 
 medical conditions.\nTherefore, the statistics are likely to overestimate the actual population by some 
 unknown margin.

## User Stories

* As a user, I want to be able to add a new patient, and any relevant information about that patient, into my
collection. 
* As a user, I want to be able to update any information about a patient, in the event that the status of any of 
the parameters has changed. 
* As a user, I want to calculate the likelihood for developing allergies given that someone has just been diagnosed
with eczema.
* As a user, I would like to calculate the odds ratio for developing a food allergy based on whether or not they 
were afflicted with eczema.
* As a user, I would like to calculate the relative risk for developing a food allergy based on whether or not they
were afflicted with eczema.
* As a user, I want to be able to specify some of the parameters to see how that may affect the likelihood of 
developing food allergies (for Phase 1, parameters will be limited to sex, will incorporate further subsets when
integrating persistence into the application). 
* As a user, I want to be able to remove a patient in the event that there is a misdiagnosis at any of the stages. 
* As a user, I want to be able to view the size of the collection - with the option of doing so based on any of the
parameters. 
* As a user, I want to be able to retrieve a patient's information by an identification number. 
* As a user, I would like the addition of each patient file be saved into the database.
* As a user, I would like to be prompted with the option of saving the database upon quitting the application.
* As a user, I would like to be able to reload the state of the database exactly where they left off at a later time.
* As a user, I would like to be able to view more information about the analyses provided by the application.
* As a user, I would like to be able to view the collection of data in a table format.

## Instructions for Grader

* From the home screen, you can generate the first required event by clicking on the "Update Database" button, then 
there is a button on the bottom-left called "Add Patient". Clicking that button should prompt you to to a window 
where you can create a new patient file. Enter information for each category (**NOTE**: the ID must be a number that 
doesn't already exist in the collection for the patient to be added - integer values 1 to 4453 are taken - so you can 
choose a number like 5000). Also, if you want, you can type in a country that isn't listed. Click the "Add Patient" 
button to add this patient to the collection. You can view that it is added by entering the ID number into the search 
bar followed by clicking the "Search" button adjacent to it. It should show up in the collection. 
    + You can also arrange ID number by clicking on the ID column tab name in the table view. 
* You can generate the second required event by selecting the patient you just created (or any other patient in the 
collection) and clicking the "Delete" button, followed by "Yes" on the popup. This will remove the patient you selected
from the collection. You can verify by trying to search for it. If the searched patient doesn't automatically scroll to
the patient in the database, then they aren't located in the database. 
* You can go back to the home screen using the MenuItems on the top-left of the menu bar. From the home screen, you
can navigate to any other tab. 
* You can locate my visual components (in the form of a graph) by clicking on either the "Database Information" or
"Run Analysis" buttons from the home screen. 
    + The graphs and test results will change upon adding new patients or removing existing patients from the database.  
* You can save the state of the application by clicking on the "Options" MenuItem on the menu bar, and then select the
"Save" item. You will also be prompted if you would like to save the database upon quitting the application. 
* You can also reload the database to its original state (with 4453 patient files) in the "Options" menu item, followed
by the "Load Original Database..." item. 
    + The option of loading an empty database isn't supported because the analyses included require a robust population
    size in order for the results to be somewhat interesting.
* The "About" item in the "Options" menu provides some further information about the application. 
* The "About Databases" item in the "Options" menu will be updated if I ever manage to get actual raw data from a study.

## Phase 4: Task 2

Selected Java language construct to add:   

> Test and design a class that is robust.  You must have at least one method that throws a checked exception. You 
 must have one test for the case where the exception is expected and another where the exception is not expected.

In the methods in the Database class have been overhauled to accommodate a robust design. In particular, there were 
3 different Exception cases that needed to be accounted for. 

* The first such instance is the ExistingPatientException, which occurs in the `addPatient` method. This exception 
is thrown in cases where the ID number entered for a new patient already exists in the database, thereby ensuring that 
there are files that share the same patient ID. 
* The next case is the NoPatientFoundException that occurs in the `removePatient` and `getPatientFileByID` methods. This
exception is thrown in scenarios where the patient specified in the parameters (via the patient's ID) does not exist
in the database. This allows for more informative responses to be issued by the console UI and GUI in scenarios where
it gets thrown.
* The third instance is the UndefinedNumberException. This exception is thrown as a result of some undefined result
in any of the statistical tests, and it is thrown in the following methods: calcProbFoodAllergy (both versions), 
`calcProbNoFoodAllergy`\*, `calcProbEczema`\*, `calcProbNoEczema`\*, `calcProbEczemaAndAllergy`\*, 
`calcProbNoEczemaAndAllergy`\*, `calcProbAllergyGivenEczema`\*, `probAllergyGivenEczemaHelperFemaleSubset`\*, 
`calcProbNoAllergyGivenEczema`\*, `calcProbAllergyGivenNoEczema`\*, `calcProbNoAllergyGivenNoEczema`\*,
`calcOddsRatio`\*, `calcRelativeRisk`\*. The \* denotes that the exception is thrown for all overloaded methods 
with corresponding to the method list provided. The exception ensures that the application user is notified (or 
prevented from being mislead) by test results with an undefined result.
  
Together, the exceptions ensure that the implementation of the stated user stories can be executed in a robust manner.   

## Phase 4: Task 3
 
Evaluation of the overall structure of the code revealed three particular areas located in the Database class that 
exhibited a high degree of coupling and/or poor cohesion. These areas were identified as primary concerns when 
considering how the application would likely evolve to incorporate new concepts or add further functionality over time. 
In particular, the growth of an application of this nature would almost certainly entail the ability to support more 
varied ways of subsetting the main patient database (e.g. subset based on age, ethnicity, geographic location, etc.). 
Creating support for more subsets would be a likely route since it could provide insight to more specific 
sub-populations that could reveal some interesting and underappreciated trends. Another likely area of growth would
be the addition of new functionality in the form of statistical analyses. 

Prior to refactorization of the code there were three groups of method in the Database class responsible for carrying
out important functionality in the application that were identified as having particularly poor cohesion. 
The different operations consisted of the following:

1. Getting the number of Patients in the database that were afflicted with a particular medical condition (or 
combination of medical conditions).
2. Calculating the proportion of patients that were afflicted with a particular medical condition (or combination of 
medical conditions).
3. Calculating the conditional probability associated with the two medical conditions.
4. Calculating different measures of association between the two medical conditions. 

The original code structure consisted of methods for every possible combination of medical conditions in the database,
which resulted in an abundance of code duplication, and poor readability. For example, to get the number of patients
with a certain medical conditions, the following methods existed: `getNumAllergy`*, `getNumEczema`*, 
 `getNumEczemaAndAllergy`*, `getNumNoEczemaAndAllergy`*,  `getNumEczemaAndNoAllergy`*, `getNumNoEczemaAndNoAllergy`*. 
All the aforementioned methods had the same code structure, with minor differences corresponding to the combination 
of medical conditions of interest. To exacerbate the issue, the '*' denoted the fact that they were overloaded methods,
with the addition of the parameter used to represent the desired subset in the database. This means that the code was
duplicated to an even greater extent with the overloading aspect of the code. This is also the case for the other two
methods, to varying extents. This example highlights how the addition of a subset, or a new test would increasingly
result in further code duplication. 

The primary modifications to address the outlined issues of cohesion are as follows:

The first change considers the notion of having a single point of control, which aids in addressing the code 
duplication in having to call a different method for every single case, where each method is structurally similar. This
change saw the implementation of 4 new methods. Each method was responsible for carrying out one of the primary 
operations offered by the application -- i.e. they cover all the different variations that could come about in the 
program in the operation. This means that there was a single operation created for the 3 operations identified during
the evaluation. The end result still required a number of functions that were practically the same but differed in a
key position. This initially suggested the use of a Composite pattern, however, it was challenged due to the fact that 
methods rely on another class (the Patient class). The solution involved moving these functions into a separate class
(the NumberOfPatientsAfflicted class) that extends the Database class. This improved readability by separating methods
that acted as helper functions into a separate class and changing them to static methods. 

The second change addressed the code duplication that arose as a result of subsetting the database. The previous 
iteration of the program saw method overloading as a solution to specify a particular subset when carrying out an 
analysis. Furthermore, the overloaded method consisted of duplicated code for each case (subset), which was not a 
feasible method to scale when incorporating more subsets than is provided at this time. This solution involved 
incorporating the process of deciding of whether to subset as a parameter to the newly designed methods. This parameter
would then locally call another function that determines the specified subset in the parameter of the method, and 
returns the appropriate database to be directly acted on. This change eliminated the code duplication that resulted from
the method overloading. 

The third change involved the implementation of enumerations to specify all possible combinations of medical conditions, 
cases of conditional probability, subsets of the database, and statistical tests. By separating the types of input 
parameters and further categorizing the different input parameters, we are improving cohesion through the organization 
of the different possible combinations of input parameters thereby separating the process of defining the necessary 
information to obtain the desired output from the 4 main methods added. In other words, this addition improves cohesion 
by separating the action of determining the specifics of a given operation from the actual method and 
creates a foundation that is much more readable and flexible to change (addition or removal). The enumerations also 
instill consistency in how the  methods are called, which can result in fewer errors due to typo. 

As a result of these changes, the hierarchy below provides the new methods in the Database class. In each sub-hierarchy 
are all the methods that the added method was able to replace. The '*' denotes methods that were overloaded (to 
accommodate the handling of different subsets of the database). 

* `numberOfPatientsWithMedicalCondition`
    + `getNumAllergy`*
    + `getNumEczema`*
    + `getNumEczemaAndAllergy`*
    + `getNumNoEczemaAndAllergy`*
    + `getNumEczemaAndNoAllergy`*
    + `getNumNoEczemaAndNoAllergy`*
* `calculateProbabilityWithMedicalCondition`
    + `calcProbFoodAllergy`*
    + `calcProbNoFoodAllergy`*
    + `calcProbEczema`*
    + `calcProbNoEczema`*
    + `calcProbEczemaAndAllergy`*
    + `calcProbNoEczemaAndAllergy`*
* `calculateConditionalProbability`
    + `calcProbAllergyGivenEczema`*
    + `calcProbNoAllergyGivenEczema`*
    + `calcProbAllergyGivenNoEczema`*
    + `calcProbNoAllergyGivenNoEczema`*
* `calculateMeasureOfAssociation`
    + `calcOddsRatio`*
    + `calcRelativeRisk`*
* `selectedSubset`
* `validSubset`    

The following classes were added in the refactorization process:

Added Enumerations

* ConditionalProbabilityCases
* DatabaseSubsets
* MeasureOfAssociation
* MedicalCondition

Added class(es)

* NumberOfPatientsAfflicted (extends Database)
    
In summary, the addition of the 6 methods above resulted in the elimination of 36 methods (when including the 
overloading). The refactoring creates a structure that is more robust in its ability to take on new subsets, while also
reducing the number of methods that would need to be called by the user interfaces to a single method per operation. 
