package com.tagtraum.perf.gcviewer.imp;

import java.io.InputStream;

import junit.framework.TestCase;

import com.tagtraum.perf.gcviewer.model.GCModel;

/**
 * Tests some cases for java 1.5 (using DataReaderSun1_6_0).
 *
 * Date: Jan 30, 2002
 * Time: 5:53:55 PM
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 * @version $Id: $
 */
public class TestDataReaderSun1_5_0 extends TestCase {

    public TestDataReaderSun1_5_0(final String name) {
        super(name);
    }

    /**
     * Test output for -XX:+PrintAdaptiveSizePolicy 
     */
    public void testAdaptiveSizePolicy() throws Exception {
        InputStream in = getClass().getResourceAsStream("SampleSun1_5_0AdaptiveSizePolicy.txt");
        DataReader reader = new DataReaderSun1_6_0(in);
        GCModel model = reader.read();
        
        assertEquals("number of events", 6, model.getPause().getN());
        assertEquals("number of full gcs", 1, model.getFullGCPause().getN());
        assertEquals("number of gcs", 5, model.getGCPause().getN());
        assertEquals("total pause", 0.1024222, model.getPause().getSum(), 0.000001);
        assertEquals("full gc pause", 0.0583435, model.getFullGCPause().getSum(), 0.000001);
        assertEquals("gc pause", 0.0440787, model.getGCPause().getSum(), 0.000001);
    }
    
    public void testCMSPrintGCDetails() throws Exception {
        final InputStream in = getClass().getResourceAsStream("SampleSun1_5_0CMS_PrintGCDetails.txt");
        final DataReader reader = new DataReaderSun1_6_0(in);
        GCModel model = reader.read();
        
        assertEquals("size", 515, model.size());
        assertEquals("throughput", 88.28044896, model.getThroughput(), 0.00000001);
        assertEquals("sum of pauses", model.getPause().getSum(), model.getFullGCPause().getSum() + model.getGCPause().getSum(), 0.0000001);
        assertEquals("total pause", 9.1337492, model.getPause().getSum(), 0.0000001);
        assertEquals("full gc pause", 7.4672903, model.getFullGCPause().getSum(), 0.00000001);
    }

    public void testParallelOldGC() throws Exception {
        final InputStream in = getClass().getResourceAsStream("SampleSun1_5_0ParallelOldGC.txt");
        final DataReader reader = new DataReaderSun1_6_0(in);
        GCModel model = reader.read();
        
        assertEquals("size", 1, model.size());
        assertEquals("gc pause", 27.0696262, model.getFullGCPause().getMax(), 0.000001);
    }

    public void testCMSIncrementalPacing() throws Exception {
        final InputStream in = getClass().getResourceAsStream("SampleSun1_5_0CMS_IncrementalPacing.txt");
        final DataReader reader = new DataReaderSun1_6_0(in);
        GCModel model = reader.read();

        assertEquals("size", 810, model.size());
        assertEquals("throughput", 94.17869828, model.getThroughput(), 0.00000001);
        assertEquals("total gc pause", 2.3410947, model.getPause().getSum(), 0.000000001);
        assertEquals("gc pause", 2.3410947, model.getGCPause().getSum(), 0.000000001);
        assertEquals("full gc paus", 0.0, model.getFullGCPause().getSum(), 0.01);
    }

    public void testPromotionFailure() throws Exception {
        final InputStream in = getClass().getResourceAsStream("SampleSun1_5_0PromotionFailure.txt");
        final DataReader reader = new DataReaderSun1_6_0(in);
        GCModel model = reader.read();
        
        assertEquals("size", 6, model.size());
        assertEquals("throughput", 98.0932228588, model.getThroughput(), 0.00000001);
        assertEquals("gc pause", 8.413616, model.getPause().getSum(), 0.000001);
    }

    public void testCMSConcurrentModeFailure() throws Exception {
        final InputStream in = getClass().getResourceAsStream("SampleSun1_5_0ConcurrentModeFailure.txt");
        final DataReader reader = new DataReaderSun1_6_0(in);
        GCModel model = reader.read();
        
        assertEquals("size", 3417, model.size());
        assertEquals("throughput", 78.5579711577, model.getThroughput(), 0.00000001);
        assertEquals("gc pause", 181.8116798, model.getPause().getSum(), 0.000000001);
    }

}
