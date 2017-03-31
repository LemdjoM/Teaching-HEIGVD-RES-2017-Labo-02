package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.JsonObjectMapper;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.data.StudentsList;
import ch.heigvd.res.labs.roulette.net.protocol.RouletteV2Protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the client side of the protocol specification (version 2).
 *
 * @author Olivier Liechti
 */
public class RouletteV2ClientImpl extends RouletteV1ClientImpl implements IRouletteV2Client {

  @Override
  public void clearDataStore() throws IOException {
    send(RouletteV2Protocol.CMD_CLEAR);

    String answer = is.readLine();
    if (!answer.equals(RouletteV2Protocol.RESPONSE_CLEAR_DONE)) throw new IOException("wrong clear answer!");

  }

  @Override
  public void disconnect() throws IOException {

    send(RouletteV2Protocol.CMD_BYE);

    LOG.info(is.readLine());

    is.close();
    os.close();

    conn.close();
  }

  @Override
  public List<Student> listStudents() throws IOException {


    send(RouletteV2Protocol.CMD_LIST);

    StudentsList sl = JsonObjectMapper.parseJson(is.readLine(), StudentsList.class);

    return sl.getStudents();
  }


}
