package roomley.auth;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Keys.
 */
public class Keys{

	@JsonProperty("keys")
	private List<KeysItem> keys;

	/**
	 * Get keys list.
	 *
	 * @return the list
	 */
	public List<KeysItem> getKeys(){
		return keys;
	}
}