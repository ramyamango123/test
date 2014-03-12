import time
import random
import math
import thread

#
# ResourceMonitor class
#
class ResourceMonitor:

  #
  # Constructor routine to initialize the numner of samples and time to wait
  # before each sample measurement
  #
  def __init__ (self, resource_name, mi_samples, ti_seconds):
    self.resource_name = resource_name
    self.mi_samples = mi_samples
    self.ti_seconds = ti_seconds
    self.list_of_values = [ ]
    self.sum_of_values = 0
    self.number_of_samples = 0

  #
  # Don't know how to measure resource sample. Using a random number instead
  #
  def _get_current_measurement (self):
      return round(random.random() * 100)


  #
  # Start monitoring all resources.
  #
  def _Start (self):
    while True:

        #
        # Keep track of last 'n' measurements in a list, by appending new
        # measurement to th list
        #
        new_element = self._get_current_measurement()
        self.list_of_values.append(new_element)

        #
        # Calculate the sum of last 'n' samples.
        #
        self.sum_of_values += new_element
        self.number_of_samples += 1
        print self.number_of_samples

        #
        # In order to track only the last 'n' measurements, drop the oldest
        # measurement (the very first in the list), if we exceed the window
        # size. Also update the sum of the samples by getting rid of the oldest
        # measured value
        #
        if len(self.list_of_values) == self.mi_samples + 1:
            first_element = self.list_of_values[0]
            self.list_of_values.remove(first_element)
            self.sum_of_values -= first_element

        #
        # Wait before measuring the next sample
        #
        time.sleep(self.ti_seconds)

  #
  # Start a background thread to start measuring samples
  #
  def Start (self):
     self.t = thread.start_new_thread(self._Start, ())

  #
  # Stop monitoring all resources
  #
  def Stop (self):
    # self.t.stop() ###############  NOT WORKING
    pass

  #
  # Get the latest value (moving average) for the statistic 'stat_name'.
  #
  def GetStatistic (self):
    print "Total samples " + str(self.number_of_samples) + \
          str(self.mi_samples) + " of " + str(self.resource_name) + \
          " taken once every " + \
          str(self.ti_seconds) + " second(s), Last " + \
          str(self.mi_samples) + " samples' average is " + \
          str(self.sum_of_values/self.mi_samples)

#
# Create three instances to minitor CPU, IO and Memory
#
monitor_cpu = ResourceMonitor("CPU", mi_samples = 2, ti_seconds = 1)
monitor_io  = ResourceMonitor("IO",  mi_samples = 5, ti_seconds = 2)
monitor_mem = ResourceMonitor("MEM", mi_samples = 50, ti_seconds = 4)

#
# Start monitoring the resource. This creates a thread in background which
# actualy measures periodically and keeps tracks of the last 'n' measurements
#
monitor_cpu.Start()
monitor_io.Start()
monitor_mem.Start()

time.sleep(1)

#
# Find out the last 'n' measurments' average
#
monitor_cpu.GetStatistic()
monitor_io.GetStatistic()
monitor_mem.GetStatistic()

#
# Stop measurements, by stopping the threads. Not working..
#
#monitor_cpu.Stop()
#monitor_io.Stop()
#monitor_mem.Stop()

exit()