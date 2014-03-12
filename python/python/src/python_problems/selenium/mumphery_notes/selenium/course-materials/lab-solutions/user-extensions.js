function RandomNumber(value)
{
return Math.floor(Math.random()*value);
}

function uc(string)
{
return string.toUpperCase();
}

function lc(string)
{
return string.toLowerCase();
}

function single_quote(string)
{
return "'" + string + "'";
}

function double_quotes(string)
{
return "\"" + string + "\"";
}

function reverse_words(string)
{
var index = string.indexOf(" ");
return string.substring(index+1) + ", " + string.substring(0,index);
}
