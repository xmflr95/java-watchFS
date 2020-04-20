package watch2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		String dir = "C:/Users/xmflr/Desktop/test";
		WatchService service = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(dir);
		
		path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		
		while (true) {
			WatchKey key = service.take();
			List<WatchEvent<?>> list = key.pollEvents();
			for (WatchEvent<?> event : list) {
				Kind<?> kind = event.kind();
				Path pth = (Path) event.context();
				if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
					// 생성
					System.out.println("생성 : " + pth.getFileName());
				} else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
					// 삭제
					System.out.println("삭제 : " + pth.getFileName());
				} else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
					// 수정
					System.out.println("수정 : " + pth.getFileName());
				}
			}
			if (!key.reset()) break;
		}
		service.close();
	}

}
