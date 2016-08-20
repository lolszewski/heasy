echo "################################";
echo "reinstalling java jdk and jre...";

if ! [ -z "$(which java)" ]; 
then
	echo "java is already installed"
	echo "starting uninstall"
	echo "java is being uninstalling..."

	dpkg-query -W -f='${binary:Package}\n' | grep -E -e '^(ia32-)?(sun|oracle)-java' -e '^openjdk-' -e '^icedtea' -e '^(default|gcj)-j(re|dk)' -e '^gcj-(.*)-j(re|dk)' -e '^java-common' | xargs sudo apt-get -y remove &> /dev/null

	dpkg -l | grep ^rc | awk '{print($2)}' | xargs sudo apt-get -y purge &> /dev/null

	sudo bash -c 'ls -d /home/*/.java' | xargs sudo rm -rf  > /dev/null

	sudo rm -rf /usr/lib/jvm/*  &> /dev/null

	for g in ControlPanel java java_vm javaws jcontrol jexec keytool mozilla-javaplugin.so orbd pack200 policytool rmid rmiregistry servertool tnameserv unpack200 appletviewer apt extcheck HtmlConverter idlj jar jarsigner javac javadoc javah javap jconsole jdb jhat jinfo jmap jps jrunscript jsadebugd jstack jstat jstatd native2ascii rmic schemagen serialver wsgen wsimport xjc xulrunner-1.9-javaplugin.so; do sudo update-alternatives --remove-all $g; done  &> /dev/null

	sudo updatedb  &> /dev/null
	sudo locate -b '\pack200'

	echo "java has been unsintalled successfully"
fi


if ! [ -z "$(which java)" ]; 
then
	echo "old version of java is still installed, finishing installator with error"
else
	echo "starting installing new version of java"
	echo "new version of java is being installing..."

	sudo apt-get -y install default-jre &> /dev/null
	sudo apt-get -y install default-jdk &> /dev/null
	
	echo "new version of java has been successfully installed"
fi

echo "reinstalling java jdk and jre finished";
echo "################################";

if ! [ -z "$(which java)" ]; 
then
	echo success
else
	echo error
fi
