##Build project

<code>mvn clean install</code>

<hr>

##Create project by archetype

We're 2 options using interactive mode:
</br>
</br>

1- No project details
<code><pre>
mvn archetype:generate -DarchetypeGroupId=com.sensedia.archetypes -DarchetypeArtifactId=connector-archetype -DarchetypeVersion=1.0-SNAPSHOT 
</pre>
</code> 
</br>

</br>

2- With project details
<code><pre>mvn archetype:generate -DarchetypeGroupId=com.sensedia.archetypes \ 
                       -DarchetypeArtifactId=connector-archetype \
                       -DarchetypeVersion=1.0-SNAPSHOT \ 
                       -DgroupId=**<your groupId>** \
                       -DartifactId=**<your artifactId>** \
                       -Dversion=**<your version>**</pre></code> </br>