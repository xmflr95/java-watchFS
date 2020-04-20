package watch;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Program {

	private static Path sharedDirectoryPath;
	
	private static WatchKey watchKey;
	private static WatchService watchService;
	
	public static void main(String[] args) {
		// 모니터링 할 파일들을 포함함 디레거리의 경로 설정
		String path = "C:/Users/xmflr/Desktop/test";
		
		sharedDirectoryPath = Paths.get(path);
		
		try {
			// FS를 watch 할 수 있는 인스턴스를 얻는다.
			watchService = FileSystems.getDefault().newWatchService();
			// WatchKey에 WatchService 객체와 감지할 이벤트를 등록한다!
			watchKey = sharedDirectoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			// 이벤트가 발생할 떄 마다 콘솔로 출력한다.
			for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
				System.out.println(watchEvent.context() + " " + watchEvent.kind());
			}
		}
		
	}

}
