<h1>Custom Wings</h1>

This is the fork of [Tigeax's CustomWings Plugin]

[Tigeax's CustomWings Plugin]: https://www.spigotmc.org/resources/customwings-1-13-1-16-1.59912/

This fork moves entire project to maven so you can use it as a dependency!

[![](https://jitpack.io/v/YouHaveTrouble/CustomWings.svg)](https://jitpack.io/#YouHaveTrouble/CustomWings)

## Maven Dependency:



	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

---

    <dependency>
	    <groupId>com.github.YouHaveTrouble</groupId>
	    <artifactId>CustomWings</artifactId>
	    <version>Tag</version>
	</dependency>
	
## Gradle:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
---
	
	dependencies {
    	        implementation 'com.github.YouHaveTrouble:CustomWings:Tag'
    	}
