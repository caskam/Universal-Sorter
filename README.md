# Universal Sorter

A GUI text and number sorter created with JavaFX using FXML and Java SE 8.

---------------------------------------------------------------------

Purpose:

Automatically identifies the nature of the content, and sorts it according to the order specification.

Instructions: 

1. Enter or load text/numbers from a plain text file (.txt) or a rich text file (.rtf). Or simply enter text/numbers into the  text area.
2. Ensure that each element is seperated with your specified seperator. The default is a comma: ','.
3. Specify the order in which to sort the content, either ascending or descending.
4. Press 'SORT' once to sort the elements.

Behind the scenes:

- If only decimals entered: The sorted elements will be decimals.
- If only integers entered: The sorted elements will be integers.
- If both integers and decimals entered: The sorted elements will be decimals.

Safety features:

- If the majority of the elements are text and numbers are detected in some elements, they will be removed.
- If the majority of the elements are numbers and text is detected in some elements, they will be removed.
- If blank elements are detected they will be removed.
- If unrecognised caracters are detected, they will be removed.
- If neither text or numbers are detected, the user will be informed and nothing will happen.

Capabilies:

- Numerical sort: Each element can have the maximum capacity of a double: 1.7 x 10^308 approximately.
- Alphabetical sort: Each element will have an unrestricted length as a String.

Examples:

  Alphabetical sort:

![Alt text](/screenshots/manual.png?raw=true "text-before")
![Alt text](/screenshots/manual.png?raw=true "text-after")

  Numerical sort:

![Alt text](/screenshots/manual.png?raw=true "numbers-before")
![Alt text](/screenshots/manual.png?raw=true "numbers-after")

OS specific issues:

- OSX: No known issues.
- Linux: No known issues.
- Windows: No known issues.
