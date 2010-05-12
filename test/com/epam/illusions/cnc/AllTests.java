package com.epam.illusions.cnc;

import org.junit.runner.*;
import org.junit.runners.*;

import com.epam.illusions.cnc.inventory.ControllerTest;
import com.epam.illusions.cnc.inventory.MachineTest;
import com.epam.illusions.cnc.inventory.model.DoublePoint3DTest;
import com.epam.illusions.cnc.inventory.visualization.MachineViewTest;
import com.epam.illusions.cnc.myutil.LineTest;
import com.epam.illusions.cnc.operator.BuilderTest;
import com.epam.illusions.cnc.operator.VirtualCncTest;
import com.epam.illusions.cnc.operator.model.CncByteTest;
import com.epam.illusions.cnc.operator.model.Point3DTest;
import com.epam.illusions.cnc.operator.parser.ase.AceCCParserTest;
import com.epam.illusions.cnc.operator.parser.bmp.BmpParserTest;
import com.epam.illusions.cnc.operator.storage.DataStoragesPerfomanceTest;
import com.epam.illusions.cnc.operator.storage.VertexBatchTest;
import com.epam.illusions.cnc.operator.storage.PromptDataStorageTest;
import com.epam.illusions.cnc.operator.storage.SimpleDataStorageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value={
		DoublePoint3DTest.class,
		MachineTest.class,
		ControllerTest.class,
		BuilderTest.class,
		MachineViewTest.class,
		VirtualCncTest.class,
		Point3DTest.class,
		LineTest.class,
		CncByteTest.class,
		SimpleDataStorageTest.class,
		PromptDataStorageTest.class,
		DataStoragesPerfomanceTest.class,
		AceCCParserTest.class,
		BmpParserTest.class,
		VertexBatchTest.class,
		PrjRowCounter.class
})
public class AllTests {

}
