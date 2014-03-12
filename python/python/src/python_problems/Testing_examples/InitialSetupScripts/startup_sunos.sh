#!/bin/bash
#-----------------------------------------------------------------
# $Id:$
# Setup of the LCG-SPI software testing examples  
#
# 13/11/02  Manuel.Venancio.Gallas.Torreira@cern.ch 
# First version
# Assume that SPI external is avaliable 
#-----------------------------------------------------------------


export CC  /afs/cern.ch/project/sun/solaris/opt/SUNWspro7/bin/CC



#=================================================================
# Specific stuff for LCG
#=================================================================
# To set the path for LCG stuff (CppUnit, Oval, ...)
 export PATH=/afs/cern.ch/sw/lcg/external/packages/0.0.1/sunos58/bin:$PATH
 echo "--------------------------------------------------------------------"
 echo "  LCG stuff                                                         "
 echo "--------------------------------------------------------------------"
 echo "Your path has been set to have access to LCG stuff (CppUnit, Oval)" 

#=================================================================
# CppUnit specific stuff 
#=================================================================
#Checks if LD_LIBRARY_PATH has something 
 export LD_LIBRARY_PATH=/afs/cern.ch/sw/lcg/external/packages/0.0.1/sunos58/lib:$LD_LIBRARY_PATH
  echo "--------------------------------------------------------------------"
  echo " CPPUNIT settings                                                   "
  export CPPUNIT_LDFLAGS='-L/afs/cern.ch/sw/lcg/external/CppUnit/1.8.0/sunos58/lib -lcppunit -ldl'
  export CPPUNIT_INCLUDES=/afs/cern.ch/sw/lcg/external/CppUnit/1.8.0/sunos58/include
  export ACLOCAL=/afs/cern.ch/sw/lcg/external/CppUnit/1.8.0/sunos58/share/aclocal/
  echo " USE -> "\$CPPUNIT_LDFLAGS", previously undefined, has been set to:      "
  echo "     "${CPPUNIT_LDFLAGS}
  echo " USE -> "\$CPPUNIT_INCLUDES", previously undefined, has been set to:      "
  echo "        "${CPPUNIT_INCLUDES}
  echo " USE -> "\$ACLOCAL", previously undefined, has been set to:      "
  echo "        "${ACLOCAL}
  echo "--------------------------------------------------------------------"

































