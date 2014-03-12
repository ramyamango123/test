#!/bin/tcsh
#-----------------------------------------------------------------
# $Id:$
# Setup of the LCG-SPI software testing examples  
#
# 13/11/02  Manuel.Venancio.Gallas.Torreira@cern.ch 
# First version
# Assume that SPI external is avaliable 
#-----------------------------------------------------------------

set INSTALLDIR=/afs/cern.ch/sw/lcg/external/icc/7.1.006/rh73/opt/intel

if !($?PATH) then
    setenv PATH $INSTALLDIR/compiler70/ia64/bin
else
    setenv PATH $INSTALLDIR/compiler70/ia64/bin:$PATH
endif

if !($?LD_LIBRARY_PATH) then
    setenv LD_LIBRARY_PATH $INSTALLDIR/compiler70/ia64/lib
else
    setenv LD_LIBRARY_PATH $INSTALLDIR/compiler70/ia64/lib:$LD_LIBRARY_PATH
endif

if !($?MANPATH) then
    setenv MANPATH $INSTALLDIR/compiler70/man:`man -w`
else
    setenv MANPATH $INSTALLDIR/compiler70/man:$MANPATH
endif



setenv CC  '/afs/cern.ch/sw/lcg/external/icc/7.1.006/rh73/opt/intel/compiler70/ia64/bin/ecc  '
 
#=================================================================
# Specific stuff for LCG
#=================================================================
# To set the path for LCG stuff (CppUnit, Oval, ...)
 setenv PATH /afs/cern.ch/sw/lcg/external/packages/0.0.1/rh73_ecc71/bin:$PATH
 echo "--------------------------------------------------------------------"
 echo "  LCG stuff                                                         "
 echo "--------------------------------------------------------------------"
 echo "Your path has been set to have access to LCG stuff (CppUnit, Oval..)" 

#=================================================================
# CppUnit specific stuff 
#=================================================================
#Checks if LD_LIBRARY_PATH has something 
if ($?LD_LIBRARY_PATH) then
 setenv LD_LIBRARY_PATH '/afs/cern.ch/sw/lcg/external/packages/0.0.1/rh73_ecc71/lib':$LD_LIBRARY_PATH
else
 setenv LD_LIBRARY_PATH '/afs/cern.ch/sw/lcg/external/packages/0.0.1/rh73_ecc71/lib'
endif
if ( "${?CPPUNIT_LDFLAGS}" != " " ) then
  echo "--------------------------------------------------------------------"
  echo " CPPUNIT settings                                                   "
  setenv CPPUNIT_LDFLAGS '-L/afs/cern.ch/sw/lcg/external/CppUnit/1.8.0/rh73_ecc71/lib -lcppunit -ldl'
  setenv CPPUNIT_INCLUDES   '/afs/cern.ch/sw/lcg/external/CppUnit/1.8.0/rh73_ecc71/include'
  setenv ACLOCAL  '/afs/cern.ch/sw/lcg/external/CppUnit/1.8.0/rh73_ecc71/share/aclocal'
  echo " USE -> "\$CPPUNIT_LDFLAGS", previously undefined, has been set to:      "
  echo "     "${CPPUNIT_LDFLAGS}
  echo " USE -> "\$CPPUNIT_INCLUDES", previously undefined, has been set to:      "
  echo "        "${CPPUNIT_INCLUDES}
  echo " USE -> "\$ACLOCAL", previously undefined, has been set to:      "
  echo "        "${ACLOCAL}
  echo "--------------------------------------------------------------------"
endif
