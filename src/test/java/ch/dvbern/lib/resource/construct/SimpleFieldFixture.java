package ch.dvbern.lib.resource.construct;

// public fields are the intention of this test fixture
@SuppressWarnings("PublicField")
public class SimpleFieldFixture {

	public String field1 = "field 1";
	public String field2 = "field 2";

	@Override
	public String toString() {
		return "field1=" + field1 + " field2=" + field2;
	}
}
