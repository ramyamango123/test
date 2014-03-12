#!/usr/bin/python
# -*- coding: utf-8 -*-
if __name__ == '__main__':
	# The following code allows this test to be invoked outside the harness and should be left unchanged
	import os, sys
	args = [os.path.realpath(os.path.expanduser("/usr/local/bin/raft")), "-f"] + sys.argv
	os.execv(args[0], args)
"""
Contact: Christopher Eichhorn / iWork Tools Team
"""
# This is a Raft test. For more information see http://raft.apple.com
testDescription  = ""                 		# Add a brief description of test functionality
testVersion      = "2013.05.15 v1.0.2"		# Used to differentiate between results for different versions of the test
testState        = ProductionState			# Possible values: DevelopmentState, ProductionState
def runTest(params):
	###################################################################
	sys.path.append('../Libraries')
	import bs_common, bs_icloud, bs_gilligan
	###################################################################

	bs_common.setConfigurationData()
	bs_common.writeConfigurationDataToLogFile()
	bs_common.writeResultsHeaderDataToLogFile()
	bs_common.writeOSXProfileToFile()
	
	myResultsData = bs_common.getResultsData()
	
	bs_common.writeItemToCsvFile("Skipper Automation Test Suite started on: "+ time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime()))
	bs_common.writeItemToCsvFile("OS: OS X Browser: Safari / "+ myResultsData["configurations"]["browserVersion"])
	
	bs_icloud.loadiCloudURL("xxxxxxxx")
	bs_icloud.iCloudSignIn("xxxxxxxx")	
	bs_gilligan.loadGilliganApp("xxxxxxxx", "Keynote")
	
	bs_common.writeItemToCsvFile("Gilligan login :, PASSED")
	
	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Keynote")
	
	bs_common.writeItemToCsvFile("Deleting All Existing Presentations :, PASSED")	
	
	bs_gilligan.createNewFile("xxxxxxxx", "Keynote", "Photo Portfolio")
	
	bs_common.writeItemToCsvFile("Create Presentation (Photo Portfolio Theme via + Icon) :, PASSED")
	
	bs_gilligan.fileClose("xxxxxxxx", "Presentation")
	bs_gilligan.checkFileSyncing("Keynote")	
	
	bs_gilligan.contextMenu_fileDuplicate("xxxxxxxx", "Keynote", "Presentation")
	
	bs_common.writeItemToCsvFile("Duplicate (Via Context Menu) :, PASSED")

	bs_gilligan.checkFileSyncing("Keynote")			
	bs_gilligan.contextMenu_fileDownload("xxxxxxxx", "Keynote", "Presentation copy", "PowerPoint")
	
	bs_common.writeItemToCsvFile("Download (in Powerpoint Via Context Menu) :, PASSED")
	
	bs_gilligan.fileRename("xxxxxxxx", "Keynote", "Presentation copy", "Memories_2013")
	
	bs_common.writeItemToCsvFile("Rename Presentation :, PASSED")
	
	bs_gilligan.checkFileSyncing("Keynote")	

	bs_gilligan.fileOpen("xxxxxxxx", "Keynote", "Memories_2013")
	
	bs_common.writeItemToCsvFile("Open existing presentation :, PASSED")
	
	bs_gilligan.fileClose("xxxxxxxx", "Memories_2013")
	bs_gilligan.checkFileSyncing("Keynote")	

	bs_gilligan.gearMenu_fileDuplicate("xxxxxxxx", "Keynote", "Memories_2013")
	
	bs_common.writeItemToCsvFile("Duplicate (Via Gear Menu) :, PASSED")
	
	bs_gilligan.checkFileSyncing("Keynote")		
	bs_gilligan.gearMenu_fileDownload("xxxxxxxx", "Keynote", "Memories_2013", "Keynote")
	
	bs_common.writeItemToCsvFile("Download (in Keynote Via Gear Menu) :, PASSED")
	
	bs_gilligan.gearMenu_fileDelete("xxxxxxxx", "Keynote", "Memories_2013")
	
	bs_common.writeItemToCsvFile("Delete Presentation (Via Gear Menu) :, PASSED")
	
	bs_gilligan.checkFileSyncing("Keynote")		
	bs_gilligan.gearMenu_fileUpload("xxxxxxxx", "Keynote", "test_upload.key")
	
	bs_common.writeItemToCsvFile("Upload Presentation :, PASSED")
	
	bs_gilligan.checkFileSyncing("Keynote")		
	
	bs_gilligan.fileOpenUploaded("xxxxxxxx", "Keynote", "test_upload")
	
	bs_common.writeItemToCsvFile("Open uploaded presentation :, PASSED")
	
	bs_gilligan.fileClose("xxxxxxxx", "test_upload")
	bs_gilligan.checkFileSyncing("Keynote")	
		
	bs_gilligan.fileOpen("xxxxxxxx", "Keynote", "test_upload")
	bs_gilligan.fileClose("xxxxxxxx", "test_upload")
	bs_gilligan.checkFileSyncing("Keynote")	

	bs_gilligan.exitGilligan("xxxxxxxx", "Keynote")
	
	bs_common.writeItemToCsvFile("Gilligan logout :, PASSED")
	
	bs_icloud.iCloudSignOut("xxxxxxxx")
	bs_common.quitSafari()
	bs_common.fileDeleteFromOSX("Memories_2013.key")
	bs_common.fileDeleteFromOSX("Presentation copy.ppt")
# 	bs_common.generateResultsEmail("Skipper")
	bs_common.formatResultsData()
	bs_common.resetConfigurationData()
	
	logPass()