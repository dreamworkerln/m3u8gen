# m3u8gen  
m3u8 playlist generator for docker mohamnag/nginx-file-browser - allow play music on (android) VLC  

1. Get running mohamnag/nginx-file-browser by  
docker run -d -p 8891:80 -v /root/documents/music/:/opt/www/files/ mohamnag/nginx-file-browser  

where in /root/documents/music/ stored you music   
Check that you can acces your files via http  


2. Run 'mvn -DskipTests package' and obtain .jar in /target  

3. Run nohup java -jar m3u8gen-0.0.1-SNAPSHOT.jar 2>&1 &
or wrap it to systemd unit or create docker image on 
