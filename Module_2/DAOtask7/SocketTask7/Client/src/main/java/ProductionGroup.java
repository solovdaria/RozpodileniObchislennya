

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionGroup {
	private Long id;
	private Long productionid;
	private String name;
	private Long parameter;
}
