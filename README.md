# Alfresco Add-on Descriptor

This project contains a [JSON Schema](http://json-schema.org/) for describing Alfresco Add-ons and a runnable Java
class that can be used to validate JSON against the schema.

The reason this exists is so that the community has a common way to describe add-ons separate from any specific
directory or listing.

Much in the same way as a pom.xml or package.json describes a project, addon.json describes an Alfresco Add-on in an
implementation-independent way.

Using this approach, Add-on developers can more easily define their projects and make those descriptions available to
other systems that might choose to index or list Add-ons to help the community find relevant and useful projects.

Rather than going to this index or that index, developers should just describe their Add-on using JSON that complies
with the schema, then place the addon.json file in the root of their project directory.

## Example Add-on Descriptor

Under `src/main/resources/examples` you will find a file called addon.json which validates cleanly against the schema.