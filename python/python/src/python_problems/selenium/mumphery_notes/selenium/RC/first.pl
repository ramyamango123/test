#! /usr/bin/perl -w

BEGIN {
unshift(@INC,"/home/selenium/selenium/selenium-perl-client-driver-0.9.2/lib");
}

use WWW::Selenium;
use Test::More;
plan tests => 1;
my $browser = WWW::Selenium->new( host=>"localhost",
                                  port => 4444,
                                  browser => "*firefox /usr/lib/firefox-1.5.0.12/firefox-bin",
                                  browser_url => "http://www.deanza.edu"
                                );
$browser->start();
$browser->open("http://www.deanza.edu/schedule/classes/?qttr=W");
$browser->type("CourseID","74");
like($browser->get_title(), qr/Searchable Schedule of Classes/);
sleep 60;
