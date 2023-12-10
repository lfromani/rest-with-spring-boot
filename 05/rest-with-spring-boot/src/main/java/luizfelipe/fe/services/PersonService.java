package luizfelipe.fe.services;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import luizfelipe.fe.exceptions.ResourceNotFoundException;
import luizfelipe.fe.mapper.DozerMapper;
import luizfelipe.fe.models.Person;
import luizfelipe.fe.repositories.PersonRepository;
import luizfelipe.fe.vos.PersonVO;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	PersonRepository personRepository;
	
	public List<PersonVO> findAll() {
		logger.info("Finding all persons");
		return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		var personFinded = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		return DozerMapper.parseObject(personFinded, PersonVO.class);
	}
	
	public PersonVO create(PersonVO personVO) {
		logger.info("Creating one person");
		var entity = DozerMapper.parseObject(personVO, Person.class);
		var entityVO = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		return entityVO;
	}
	
	public PersonVO update(PersonVO personVO) {
		logger.info("Updating one person");
		
		var personFinded = personRepository.findById(personVO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		personFinded.setFirstName(personVO.getFirstName());
		personFinded.setLastName(personVO.getLastName());
		personFinded.setAddress(personVO.getAddress());
		personFinded.setGender(personVO.getGender());
		
		var entityVO = DozerMapper.parseObject(personRepository.save(personFinded), PersonVO.class);
		return entityVO;
	}
	
	public void delete(Long id) {
		var personFinded = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		personRepository.delete(personFinded);
	}
	
}
