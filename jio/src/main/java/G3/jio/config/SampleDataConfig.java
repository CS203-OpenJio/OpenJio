// package G3.jio.config;

// import G3.jio.entities.Organiser;

// @Configuration
// public class SampleDataConfig {

// @Autowired
// private AuthService authService;

// @Autowired
// private OrganiserService organiserService;
// @Autowired
// private PromotionRepository promotionRepository;
// @Autowired
// private QuestRepository questRepository;

// @Autowired
// private MinioService minioService;

// @Bean
// CommandLineRunner commandLineRunner() {
// return args -> {

// createStudents();
// createOrganisers();
// createAdmin();
// createEvents();
// minioService.initBuckets();
// };
// }