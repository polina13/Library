String word = requestQueryParams("userInput");
word = word.toLowerCase();
String newWord = WordUtils.capitalize(word);
