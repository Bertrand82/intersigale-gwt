package bg.server;

import java.util.List;

import bg.client.SigaleService;
import bg.client.inter.cicada.beans.LexiqueMetaData;
import bg.client.inter.cicada.beans.UserBean;
import bg.server.inter.cicada.data.ConstanteEntity;
import bg.server.inter.cicada.data.ConstanteEntityFactory;
import bg.server.inter.cicada.data.LexiqueEntityFactory;
import bg.server.inter.cicada.data.UserEntity;
import bg.server.inter.cicada.data.UserEntityFactory;
import bg.server.util.UtilCopyBean;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SigaleServiceImpl extends RemoteServiceServlet implements SigaleService {

	@Override
	public long storeLexiqueMetadata(LexiqueMetaData lexique) throws Exception {
		long id = LexiqueEntityFactory.getInstance().makePersistent(lexique);
		return id;
	}

	@Override
	public List<LexiqueMetaData> getListLexiquesByOwner(String email) throws Exception {

		return LexiqueEntityFactory.getInstance().getListLexiqueLight();
	}

	@Override
	public boolean deleteLexiqueMetadata(String lexiqueId, String emailUser) throws Exception {
		LexiqueEntityFactory.getInstance().removeLexique(lexiqueId, emailUser);
		return true;
	}

	@Override
	public LexiqueMetaData getLexiqueMetadataById(String lexiqueId, String email) throws Exception {
		LexiqueMetaData lmd = LexiqueEntityFactory.getInstance().getLexique(lexiqueId);
		return lmd;
	}

	@Override
	public UserBean register(UserBean userBean) throws Exception {
		UserEntity userEntity = UtilCopyBean.copyTo(UserEntity.class, userBean);
		UserEntity userEntity2 = UserEntityFactory.getInstance().register(userEntity);
		UserBean userBean2 = UtilCopyBean.copyTo(UserBean.class, userEntity2);
		return userBean2;
	}

	@Override
	public UserBean login(String email, String password) throws Exception {
		UserEntity userEntity = UserEntityFactory.getInstance().getUserEntity(email, password);
		
		UserBean userBean = UtilCopyBean.copyTo(UserBean.class, userEntity);
		if (userBean == null) {
			throw new Exception("No User or password for " + email);
		}
		return userBean;
	}

	@Override
	public String getKey(UserBean userBean) throws Exception {
		
		UserEntity userEntity = UserEntityFactory.getInstance().getUserEntity(userBean.getEmail());
		if (userEntity==null){
			throw new Exception("No registered "+userBean.getEmail());
		}
		String key = "AIzaSyDouqYxNhF8U1vfI7YM7uzEzZi9DjYzJQ4";
		ConstanteEntity constante = new ConstanteEntity();
		constante.setKey("KeyGT");;
		constante.setValue(key);;
		constante = ConstanteEntityFactory.getInstance().persisteValue(constante);
		return constante.getValue();
	}

}
