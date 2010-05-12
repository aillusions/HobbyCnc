package com.epam.illusions.cnc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.epam.illusions.cnc.inventory.ControllerTest;
import com.epam.illusions.cnc.inventory.MachineTest;
import com.epam.illusions.cnc.inventory.model.DoublePoint3DTest;
import com.epam.illusions.cnc.inventory.visualization.MachineViewTest;
import com.epam.illusions.cnc.operator.BuilderTest;
import com.epam.illusions.cnc.operator.VirtualCncTest;
import com.epam.illusions.cnc.operator.model.CncByteTest;
import com.epam.illusions.cnc.operator.model.Point3DTest;
import com.epam.illusions.cnc.operator.parser.ase.AceCCParserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value={
		DoublePoint3DTest.class,
		MachineTest.class,
		ControllerTest.class,
		BuilderTest.class,
		MachineViewTest.class,
		VirtualCncTest.class,
		Point3DTest.class,
		CncByteTest.class,
		AceCCParserTest.class,
		PrjRowCounter.class
})
public class AllTests {

}
