# construct

**This library was mainly put up for archival purposes**

Instantiate and manipulate Java objects parsed from an Xml structure.

## Getting Started

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<script>
	<vardef name="myObject">
		<!-- instantiate the Java class with a no-args constructor -->
		<construct class="ch.dvbern.lib.resource.construct.SimpleFieldFixture"/>
	</vardef>
	<vardef name="field1">
		<getfield name="field1">
			<target>
				<var name="myObject"/>
			</target>
		</getfield>
	</vardef>
	<setfield name="field2">
		<target>
			<var name="myObject"/>
		</target>
		<value>
			<var name="field1"/>
		</value>
	</setfield>
	<return>
		<var name="myObject"/>
	</return>
</script>
```

### Installing

Using maven:

```xml

<dependency>
	<groupId>ch.dvbern.oss.construct</groupId>
	<artifactId>construct</artifactId>
	<!-- see tags/master branch -->
	<version>x.y.z</version>
</dependency>
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing Guidelines

Please read [Contributing.md](CONTRIBUTING.md) for the process for submitting pull requests to us.

## Code of Conduct

One healthy social atmospehere is very important to us, wherefore we rate our Code of Conduct high. For details check
the file [CodeOfConduct.md](CODE_OF_CONDUCT.md)

## Authors

* **DV Bern AG** - *Initial work* - [dvbern](https://github.com/dvbern)

See also the list of [contributors](https://github.com/dvbern/doctemplate/contributors) who participated in this
project.

## License

This project is licensed under the Apache 2.0 License - see the [License.md](LICENSE.md) file for details.

