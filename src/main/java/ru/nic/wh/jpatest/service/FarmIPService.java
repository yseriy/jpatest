package ru.nic.wh.jpatest.service;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import ru.nic.wh.jpatest.domain.FarmIP;
//import ru.nic.wh.jpatest.repository.FarmIPRepository;
//import ru.nic.wh.jpatest.web.Command;
//
//@Service
//@Transactional
//public class FarmIPService {
//
//	private final FarmIPRepository farmipRepository;
//
//	@Autowired
//	public FarmIPService(FarmIPRepository farmipRepository) {
//		this.farmipRepository = farmipRepository;
//	}
//
//	public List<FarmIP> list() {
//		List<FarmIP> farmipList = new ArrayList<>();
//
//		for (FarmIP farmip : farmipRepository.findAll())
//			farmipList.add(farmip);
//
//		return farmipList;
//	}
//
//	public void save(Command<FarmIP> command) {
//		farmipRepository.save(command.execute());
//	}
//}
